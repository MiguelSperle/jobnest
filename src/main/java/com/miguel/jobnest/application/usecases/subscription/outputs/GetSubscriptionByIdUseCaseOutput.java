package com.miguel.jobnest.application.usecases.subscription.outputs;

import com.miguel.jobnest.domain.entities.Subscription;

public record GetSubscriptionByIdUseCaseOutput(
        Subscription subscription
) {
    public static GetSubscriptionByIdUseCaseOutput from(final Subscription subscription) {
        return new GetSubscriptionByIdUseCaseOutput(subscription);
    }
}
