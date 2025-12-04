package com.miguel.jobnest.application.usecases.subscription.inputs;

public record GetSubscriptionByIdUseCaseInput(
        String subscriptionId
) {
    public static GetSubscriptionByIdUseCaseInput with(String subscriptionId) {
        return new GetSubscriptionByIdUseCaseInput(subscriptionId);
    }
}
