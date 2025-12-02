package com.miguel.jobnest.application.usecases.subscription.inputs;

public record UpdateSubscriptionUseCaseInput(
        String id
) {
    public static UpdateSubscriptionUseCaseInput with(String id) {
        return new UpdateSubscriptionUseCaseInput(id);
    }
}
