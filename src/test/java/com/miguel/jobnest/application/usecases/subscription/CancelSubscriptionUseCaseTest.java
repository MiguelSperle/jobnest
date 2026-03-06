package com.miguel.jobnest.application.usecases.subscription;

import com.miguel.jobnest.application.abstractions.repositories.SubscriptionRepository;
import com.miguel.jobnest.application.usecases.subscription.inputs.CancelSubscriptionUseCaseInput;
import com.miguel.jobnest.domain.builders.SubscriptionBuilder;
import com.miguel.jobnest.domain.entities.Subscription;
import com.miguel.jobnest.domain.exceptions.NotFoundException;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

@ExtendWith(MockitoExtension.class)
public class CancelSubscriptionUseCaseTest {
    @InjectMocks
    private DefaultCancelSubscriptionUseCase useCase;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Test
    void shouldCancelSubscription_whenCallExecute() {
        final Subscription subscription = SubscriptionBuilder.subscription().id(IdentifierUtils.generateNewId()).isCanceled(false).build();

        final CancelSubscriptionUseCaseInput input = CancelSubscriptionUseCaseInput.with(subscription.getId());

        Mockito.when(this.subscriptionRepository.findById(Mockito.any())).thenReturn(Optional.of(subscription));
        Mockito.when(this.subscriptionRepository.save(Mockito.any())).thenAnswer(returnsFirstArg());

        this.useCase.execute(input);

        Mockito.verify(this.subscriptionRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.subscriptionRepository, Mockito.times(1)).save(Mockito.argThat(Subscription::getIsCanceled));
    }

    @Test
    void shouldThrowNotFoundException_whenCallExecute_becauseSubscriptionDoesNotExist() {
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
