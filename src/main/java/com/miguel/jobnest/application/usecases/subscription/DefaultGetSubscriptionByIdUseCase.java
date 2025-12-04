package com.miguel.jobnest.application.usecases.subscription;

import com.miguel.jobnest.application.abstractions.repositories.SubscriptionRepository;
import com.miguel.jobnest.application.abstractions.usecases.subscription.GetSubscriptionByIdUseCase;
import com.miguel.jobnest.application.usecases.subscription.inputs.GetSubscriptionByIdUseCaseInput;
import com.miguel.jobnest.application.usecases.subscription.outputs.GetSubscriptionByIdUseCaseOutput;
import com.miguel.jobnest.domain.entities.Subscription;
import com.miguel.jobnest.domain.exceptions.NotFoundException;

public class DefaultGetSubscriptionByIdUseCase implements GetSubscriptionByIdUseCase {
    private final SubscriptionRepository subscriptionRepository;

    public DefaultGetSubscriptionByIdUseCase(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public GetSubscriptionByIdUseCaseOutput execute(GetSubscriptionByIdUseCaseInput input) {
        final Subscription subscription = this.getSubscriptionById(input.subscriptionId());

        return GetSubscriptionByIdUseCaseOutput.from(subscription);
    }

    private Subscription getSubscriptionById(String id) {
        return this.subscriptionRepository.findById(id).orElseThrow(() -> NotFoundException.with("Subscription not found"));
    }
}
