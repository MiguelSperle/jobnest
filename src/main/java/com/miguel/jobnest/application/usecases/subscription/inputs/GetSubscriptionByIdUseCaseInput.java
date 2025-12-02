package com.miguel.jobnest.application.usecases.subscription.inputs;

public record GetSubscriptionByIdUseCaseInput(
        String id
) {
    public static GetSubscriptionByIdUseCaseInput with(String id) {
        return new GetSubscriptionByIdUseCaseInput(id);
    }
}
