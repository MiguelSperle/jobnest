package com.miguel.jobnest.application.usecases.subscription;

import com.miguel.jobnest.application.abstractions.repositories.SubscriptionRepository;
import com.miguel.jobnest.application.abstractions.services.SecurityService;
import com.miguel.jobnest.application.abstractions.services.UploadService;
import com.miguel.jobnest.application.usecases.subscription.inputs.CreateSubscriptionUseCaseInput;
import com.miguel.jobnest.domain.builders.JobVacancyBuilder;
import com.miguel.jobnest.domain.builders.UserBuilder;
import com.miguel.jobnest.domain.entities.JobVacancy;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.exceptions.DomainException;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
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

    @Test
    void shouldCreateSubscription_whenCallExecute() {
        final User userCandidate = UserBuilder.user().id(IdentifierUtils.generateNewId()).build();
        final JobVacancy jobVacancy = JobVacancyBuilder.jobVacancy().id(IdentifierUtils.generateNewId()).build();

        final String resumeUrl = "resume-url/resume-file/1Ab.pdf";

        final CreateSubscriptionUseCaseInput input = CreateSubscriptionUseCaseInput.with(
                new byte[0],
                jobVacancy.getId()
        );

        Mockito.when(this.securityService.getPrincipal()).thenReturn(userCandidate.getId());
        Mockito.when(this.subscriptionRepository.existsByUserIdAndJobVacancyId(Mockito.any(), Mockito.any())).thenReturn(false);
        Mockito.when(this.uploadService.uploadFile(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(resumeUrl);
        Mockito.when(this.subscriptionRepository.save(Mockito.any())).thenAnswer(returnsFirstArg());

        this.useCase.execute(input);

        Mockito.verify(this.securityService, Mockito.times(1)).getPrincipal();
        Mockito.verify(this.subscriptionRepository, Mockito.times(1)).existsByUserIdAndJobVacancyId(Mockito.any(), Mockito.any());
        Mockito.verify(this.uploadService, Mockito.times(1)).uploadFile(Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.verify(this.subscriptionRepository, Mockito.times(1)).save(Mockito.argThat(subscriptionSaved ->
                Objects.nonNull(subscriptionSaved.getId()) &&
                        Objects.equals(subscriptionSaved.getUserId(), userCandidate.getId()) &&
                        Objects.equals(subscriptionSaved.getJobVacancyId(), input.jobVacancyId()) &&
                        Objects.equals(subscriptionSaved.getResumeUrl(), resumeUrl) &&
                        !subscriptionSaved.getIsCanceled() &&
                        Objects.nonNull(subscriptionSaved.getCreatedAt())
        ));
    }

    @Test
    void shouldThrowDomainException_whenCallExecute_becauseSubscriptionAlreadyExists() {
        final User userCandidate = UserBuilder.user().id(IdentifierUtils.generateNewId()).build();
        final JobVacancy jobVacancy = JobVacancyBuilder.jobVacancy().id(IdentifierUtils.generateNewId()).build();

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
        final User userCandidate = UserBuilder.user().id(IdentifierUtils.generateNewId()).build();
        final JobVacancy jobVacancy = JobVacancyBuilder.jobVacancy().id(IdentifierUtils.generateNewId()).build();

        final String resumeUrl = "resume-url/resume-file/1Ab.pdf";

        final CreateSubscriptionUseCaseInput input = CreateSubscriptionUseCaseInput.with(
                new byte[0],
                jobVacancy.getId()
        );

        Mockito.when(this.securityService.getPrincipal()).thenReturn(userCandidate.getId());
        Mockito.when(this.subscriptionRepository.existsByUserIdAndJobVacancyId(Mockito.any(), Mockito.any())).thenReturn(false);
        Mockito.when(this.uploadService.uploadFile(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(resumeUrl);
        Mockito.when(this.subscriptionRepository.save(Mockito.any())).thenThrow(new RuntimeException());
        Mockito.doNothing().when(this.uploadService).destroyFile(Mockito.any(), Mockito.any());

        Assertions.assertThrows(RuntimeException.class, () -> this.useCase.execute(input));

        Mockito.verify(this.securityService, Mockito.times(1)).getPrincipal();
        Mockito.verify(this.subscriptionRepository, Mockito.times(1)).existsByUserIdAndJobVacancyId(Mockito.any(), Mockito.any());
        Mockito.verify(this.uploadService, Mockito.times(1)).uploadFile(Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.verify(this.subscriptionRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.uploadService, Mockito.times(1)).destroyFile(Mockito.any(), Mockito.any());
    }
}
