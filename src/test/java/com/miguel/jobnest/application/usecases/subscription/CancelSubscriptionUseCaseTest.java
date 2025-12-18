package com.miguel.jobnest.application.usecases.subscription;

import com.miguel.jobnest.application.abstractions.repositories.SubscriptionRepository;
import com.miguel.jobnest.application.usecases.subscription.inputs.CancelSubscriptionUseCaseInput;
import com.miguel.jobnest.domain.entities.JobVacancy;
import com.miguel.jobnest.domain.entities.Subscription;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.enums.AuthorizationRole;
import com.miguel.jobnest.domain.enums.UserStatus;
import com.miguel.jobnest.domain.exceptions.NotFoundException;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.application.utils.JobVacancyTestBuilder;
import com.miguel.jobnest.application.utils.SubscriptionTestBuilder;
import com.miguel.jobnest.application.utils.UserTestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

@ExtendWith(MockitoExtension.class)
public class CancelSubscriptionUseCaseTest {
    @InjectMocks
    private DefaultCancelSubscriptionUseCase useCase;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Test
    void shouldCancelSubscription() {
        final User userRecruiter = UserTestBuilder.aUser().userStatus(UserStatus.VERIFIED).authorizationRole(AuthorizationRole.RECRUITER).build();
        final User userCandidate = UserTestBuilder.aUser().userStatus(UserStatus.VERIFIED).authorizationRole(AuthorizationRole.CANDIDATE).build();
        final JobVacancy jobVacancy = JobVacancyTestBuilder.aJobVacancy().userId(userRecruiter.getId()).build();
        final Subscription subscription = SubscriptionTestBuilder.aSubscription().userId(userCandidate.getId()).jobVacancyId(jobVacancy.getId()).build();

        final CancelSubscriptionUseCaseInput input = CancelSubscriptionUseCaseInput.with(subscription.getId());

        Mockito.when(this.subscriptionRepository.findById(Mockito.any())).thenReturn(Optional.of(subscription));
        Mockito.when(this.subscriptionRepository.save(Mockito.any())).thenAnswer(returnsFirstArg());

        this.useCase.execute(input);

        Mockito.verify(this.subscriptionRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.subscriptionRepository, Mockito.times(1)).save(Mockito.argThat(subscriptionSaved ->
                Objects.equals(subscriptionSaved.getIsCanceled(), true)
        ));
    }

    @Test
    void shouldThrowNotFoundException_whenSubscriptionDoesNotExist() {
        final CancelSubscriptionUseCaseInput input = CancelSubscriptionUseCaseInput.with(
                IdentifierUtils.generateNewId()
        );

        Mockito.when(this.subscriptionRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final var ex = Assertions.assertThrows(NotFoundException.class, () ->
                this.useCase.execute(input)
        );

        final String expectedErrorMessage = "Subscription not found";

        Assertions.assertEquals(expectedErrorMessage, ex.getMessage());

        Mockito.verify(this.subscriptionRepository, Mockito.times(1)).findById(Mockito.any());
    }
}
