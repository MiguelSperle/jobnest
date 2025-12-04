package com.miguel.jobnest.application.usecases.subscription;

import com.miguel.jobnest.application.abstractions.repositories.SubscriptionRepository;
import com.miguel.jobnest.application.abstractions.usecases.subscription.CancelSubscriptionUseCase;
import com.miguel.jobnest.application.usecases.subscription.inputs.CancelSubscriptionUseCaseInput;
import com.miguel.jobnest.domain.entities.Subscription;
import com.miguel.jobnest.domain.exceptions.NotFoundException;

public class DefaultCancelSubscriptionUseCase implements CancelSubscriptionUseCase {
    private final SubscriptionRepository subscriptionRepository;

    public DefaultCancelSubscriptionUseCase(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public void execute(CancelSubscriptionUseCaseInput input) {
        final Subscription subscription = this.getSubscriptionById(input.subscriptionId());

        final Subscription updatedSubscription = subscription.withIsCanceled(true);

        this.saveSubscription(updatedSubscription);
    }

    private Subscription getSubscriptionById(String id) {
        return this.subscriptionRepository.findById(id).orElseThrow(() -> NotFoundException.with("Subscription not found"));
    }

    private void saveSubscription(Subscription subscription) {
        this.subscriptionRepository.save(subscription);
    }
}
