package com.miguel.jobnest.application.usecases.subscription.inputs;

public record GetSubscriptionByIdUseCaseInput(
        String subscriptionId
) {
    public static GetSubscriptionByIdUseCaseInput with(final String subscriptionId) {
        return new GetSubscriptionByIdUseCaseInput(subscriptionId);
    }
}
