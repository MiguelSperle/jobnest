package com.miguel.jobnest.application.usecases.subscription;

import com.miguel.jobnest.application.abstractions.repositories.EventOutboxRepository;
import com.miguel.jobnest.application.abstractions.wrapper.TransactionExecutor;
import com.miguel.jobnest.application.abstractions.repositories.SubscriptionRepository;
import com.miguel.jobnest.application.abstractions.services.SecurityService;
import com.miguel.jobnest.application.abstractions.services.UploadService;
import com.miguel.jobnest.application.usecases.subscription.inputs.CreateSubscriptionUseCaseInput;
import com.miguel.jobnest.domain.entities.JobVacancy;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.enums.AuthorizationRole;
import com.miguel.jobnest.domain.enums.UserStatus;
import com.miguel.jobnest.domain.exceptions.DomainException;
import com.miguel.jobnest.application.builders.JobVacancyTestBuilder;
import com.miguel.jobnest.application.builders.UserTestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

@ExtendWith(MockitoExtension.class)
public class CreateSubscriptionUseCaseTest {
    @InjectMocks
    private DefaultCreateSubscriptionUseCase useCase;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private UploadService uploadService;

    @Mock
    private SecurityService securityService;

    @Mock
    private EventOutboxRepository eventOutboxRepository;

    @Mock
    private TransactionExecutor transactionExecutor;

    @Test
    void shouldCreateSubscription_whenCallExecute() {
        final User userCandidate = UserTestBuilder.aUser().userStatus(UserStatus.VERIFIED).authorizationRole(AuthorizationRole.CANDIDATE).build();
        final User userRecruiter = UserTestBuilder.aUser().userStatus(UserStatus.VERIFIED).authorizationRole(AuthorizationRole.RECRUITER).build();
        final JobVacancy jobVacancy = JobVacancyTestBuilder.aJobVacancy().userId(userRecruiter.getId()).build();
        final String resumeUrl = "resume-url/resume-file/1Ab.pdf";

        final CreateSubscriptionUseCaseInput input = CreateSubscriptionUseCaseInput.with(
                new byte[0],
                jobVacancy.getId()
        );

        Mockito.when(this.securityService.getPrincipal()).thenReturn(userCandidate.getId());
        Mockito.when(this.subscriptionRepository.existsByUserIdAndJobVacancyId(Mockito.any(), Mockito.any())).thenReturn(false);
        Mockito.when(this.uploadService.uploadFile(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(resumeUrl);
        Mockito.doAnswer(invocationOnMock -> {
            Runnable runnable = invocationOnMock.getArgument(0);
            runnable.run();
            return runnable;
        }).when(this.transactionExecutor).runTransaction(Mockito.any());
        Mockito.when(this.subscriptionRepository.save(Mockito.any())).thenAnswer(returnsFirstArg());
        Mockito.doNothing().when(this.eventOutboxRepository).save(Mockito.any(), Mockito.any(), Mockito.any());

        this.useCase.execute(input);

        Mockito.verify(this.securityService, Mockito.times(1)).getPrincipal();
        Mockito.verify(this.subscriptionRepository, Mockito.times(1)).existsByUserIdAndJobVacancyId(Mockito.any(), Mockito.any());
        Mockito.verify(this.uploadService, Mockito.times(1)).uploadFile(Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.verify(this.transactionExecutor, Mockito.times(1)).runTransaction(Mockito.any());
        Mockito.verify(this.subscriptionRepository, Mockito.times(1)).save(Mockito.argThat(subscriptionSaved ->
                Objects.nonNull(subscriptionSaved.getId()) &&
                        Objects.equals(subscriptionSaved.getUserId(), userCandidate.getId()) &&
                        Objects.equals(subscriptionSaved.getJobVacancyId(), input.jobVacancyId()) &&
                        Objects.equals(subscriptionSaved.getResumeUrl(), resumeUrl) &&
                        Objects.equals(subscriptionSaved.getIsCanceled(), false) &&
                        Objects.nonNull(subscriptionSaved.getCreatedAt())
        ));
        Mockito.verify(this.eventOutboxRepository, Mockito.times(1)).save(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    void shouldThrowDomainException_whenCallExecute_becauseSubscriptionAlreadyExists() {
        final User userCandidate = UserTestBuilder.aUser().userStatus(UserStatus.VERIFIED).authorizationRole(AuthorizationRole.CANDIDATE).build();
        final User userRecruiter = UserTestBuilder.aUser().userStatus(UserStatus.VERIFIED).authorizationRole(AuthorizationRole.RECRUITER).build();
        final JobVacancy jobVacancy = JobVacancyTestBuilder.aJobVacancy().userId(userRecruiter.getId()).build();

        final CreateSubscriptionUseCaseInput input = CreateSubscriptionUseCaseInput.with(
                new byte[0],
                jobVacancy.getId()
        );

        Mockito.when(this.securityService.getPrincipal()).thenReturn(userCandidate.getId());
        Mockito.when(this.subscriptionRepository.existsByUserIdAndJobVacancyId(Mockito.any(), Mockito.any())).thenReturn(true);

        final var ex = Assertions.assertThrows(DomainException.class, () ->
                this.useCase.execute(input)
        );

        final String expectedErrorMessage = "You are already subscribed in this job vacancy";
        final int expectedStatusCode = 409;

        Assertions.assertEquals(expectedErrorMessage, ex.getMessage());
        Assertions.assertEquals(expectedStatusCode, ex.getStatusCode());

        Mockito.verify(this.securityService, Mockito.times(1)).getPrincipal();
        Mockito.verify(this.subscriptionRepository, Mockito.times(1)).existsByUserIdAndJobVacancyId(Mockito.any(), Mockito.any());
    }

    @Test
    void shouldRollbackFileUpload_whenCallExecute_becauseSavingSubscriptionFails() {
        final User userCandidate = UserTestBuilder.aUser().userStatus(UserStatus.VERIFIED).authorizationRole(AuthorizationRole.CANDIDATE).build();
        final User userRecruiter = UserTestBuilder.aUser().userStatus(UserStatus.VERIFIED).authorizationRole(AuthorizationRole.RECRUITER).build();
        final JobVacancy jobVacancy = JobVacancyTestBuilder.aJobVacancy().userId(userRecruiter.getId()).build();
        final String resumeUrl = "resume-url/resume-file/1Ab.pdf";

        final CreateSubscriptionUseCaseInput input = CreateSubscriptionUseCaseInput.with(
                new byte[0],
                jobVacancy.getId()
        );

        Mockito.when(this.securityService.getPrincipal()).thenReturn(userCandidate.getId());
        Mockito.when(this.subscriptionRepository.existsByUserIdAndJobVacancyId(Mockito.any(), Mockito.any())).thenReturn(false);
        Mockito.when(this.uploadService.uploadFile(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(resumeUrl);
        Mockito.doAnswer(invocationOnMock -> {
            Runnable runnable = invocationOnMock.getArgument(0);
            runnable.run();
            return runnable;
        }).when(this.transactionExecutor).runTransaction(Mockito.any());
        Mockito.when(this.subscriptionRepository.save(Mockito.any())).thenThrow(new RuntimeException());
        Mockito.doNothing().when(this.uploadService).destroyFile(Mockito.any(), Mockito.any());

        Assertions.assertThrows(RuntimeException.class, () -> this.useCase.execute(input));

        Mockito.verify(this.securityService, Mockito.times(1)).getPrincipal();
        Mockito.verify(this.subscriptionRepository, Mockito.times(1)).existsByUserIdAndJobVacancyId(Mockito.any(), Mockito.any());
        Mockito.verify(this.uploadService, Mockito.times(1)).uploadFile(Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.verify(this.transactionExecutor, Mockito.times(1)).runTransaction(Mockito.any());
        Mockito.verify(this.subscriptionRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.uploadService, Mockito.times(1)).destroyFile(Mockito.any(), Mockito.any());
    }
}
