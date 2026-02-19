package com.miguel.jobnest.application.usecases.subscription.inputs;

public record CancelSubscriptionUseCaseInput(
        String subscriptionId
) {
    public static CancelSubscriptionUseCaseInput with(final String subscriptionId) {
        return new CancelSubscriptionUseCaseInput(subscriptionId);
    }
}
