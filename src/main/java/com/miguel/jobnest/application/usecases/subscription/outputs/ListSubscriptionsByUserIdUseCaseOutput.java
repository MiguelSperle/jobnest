package com.miguel.jobnest.application.usecases.subscription.outputs;

import com.miguel.jobnest.domain.entities.Subscription;
import com.miguel.jobnest.domain.pagination.Pagination;

public record ListSubscriptionsByUserIdUseCaseOutput(
        Pagination<Subscription> paginatedSubscriptions
) {
    public static ListSubscriptionsByUserIdUseCaseOutput from(Pagination<Subscription> paginatedSubscriptions) {
        return new ListSubscriptionsByUserIdUseCaseOutput(paginatedSubscriptions);
    }
}
