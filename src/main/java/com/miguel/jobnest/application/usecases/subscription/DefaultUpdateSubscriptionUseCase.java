package com.miguel.jobnest.application.usecases.subscription;

import com.miguel.jobnest.application.abstractions.repositories.SubscriptionRepository;
import com.miguel.jobnest.application.abstractions.usecases.subscription.UpdateSubscriptionUseCase;
import com.miguel.jobnest.application.usecases.subscription.inputs.UpdateSubscriptionUseCaseInput;
import com.miguel.jobnest.domain.entities.Subscription;
import com.miguel.jobnest.domain.exceptions.NotFoundException;

public class DefaultUpdateSubscriptionUseCase implements UpdateSubscriptionUseCase {
    private final SubscriptionRepository subscriptionRepository;

    public DefaultUpdateSubscriptionUseCase(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public void execute(UpdateSubscriptionUseCaseInput input) {
        final Subscription subscription = this.getSubscriptionById(input.id());

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
