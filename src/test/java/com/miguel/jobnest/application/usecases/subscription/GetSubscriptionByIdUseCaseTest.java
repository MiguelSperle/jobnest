package com.miguel.jobnest.application.usecases.subscription;

import com.miguel.jobnest.application.abstractions.repositories.SubscriptionRepository;
import com.miguel.jobnest.application.usecases.subscription.inputs.GetSubscriptionByIdUseCaseInput;
import com.miguel.jobnest.application.usecases.subscription.outputs.GetSubscriptionByIdUseCaseOutput;
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

@ExtendWith(MockitoExtension.class)
public class GetSubscriptionByIdUseCaseTest {
    @InjectMocks
    private DefaultGetSubscriptionByIdUseCase useCase;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Test
    void shouldGetSubscriptionById_whenCallExecute() {
        final Subscription subscription = SubscriptionBuilder.subscription().id(IdentifierUtils.generateNewId()).build();

        final GetSubscriptionByIdUseCaseInput input = GetSubscriptionByIdUseCaseInput.with(
                subscription.getId()
        );

        Mockito.when(this.subscriptionRepository.findById(Mockito.any())).thenReturn(Optional.of(subscription));

        final GetSubscriptionByIdUseCaseOutput output = this.useCase.execute(input);

        Assertions.assertNotNull(output);
        Assertions.assertNotNull(output.subscription());
        Assertions.assertEquals(subscription.getId(), output.subscription().getId());
        Assertions.assertEquals(subscription.getUserId(), output.subscription().getUserId());
        Assertions.assertEquals(subscription.getJobVacancyId(), output.subscription().getJobVacancyId());
        Assertions.assertEquals(subscription.getResumeUrl(), output.subscription().getResumeUrl());
        Assertions.assertEquals(subscription.getIsCanceled(), output.subscription().getIsCanceled());
        Assertions.assertEquals(subscription.getCreatedAt(), output.subscription().getCreatedAt());

        Mockito.verify(this.subscriptionRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    void shouldThrowNotFoundException_whenCallExecute_becauseSubscriptionDoesNotExist() {
        final GetSubscriptionByIdUseCaseInput input = GetSubscriptionByIdUseCaseInput.with(
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
