package com.miguel.jobnest.application.usecases.subscription.outputs;

import com.miguel.jobnest.domain.entities.Subscription;
import com.miguel.jobnest.domain.pagination.Pagination;

public record ListSubscriptionsByJobVacancyIdUseCaseOutput(
        Pagination<Subscription> paginatedSubscriptions
) {
    public static ListSubscriptionsByJobVacancyIdUseCaseOutput from(final Pagination<Subscription> paginatedSubscriptions) {
        return new ListSubscriptionsByJobVacancyIdUseCaseOutput(paginatedSubscriptions);
    }
}
