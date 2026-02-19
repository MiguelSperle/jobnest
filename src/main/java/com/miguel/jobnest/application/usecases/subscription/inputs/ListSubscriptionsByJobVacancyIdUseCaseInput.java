package com.miguel.jobnest.application.usecases.subscription.inputs;

import com.miguel.jobnest.domain.pagination.SearchQuery;

public record ListSubscriptionsByJobVacancyIdUseCaseInput(
        String jobVacancyId,
        SearchQuery searchQuery
) {
    public static ListSubscriptionsByJobVacancyIdUseCaseInput with(final String jobVacancyId, final SearchQuery searchQuery) {
        return new ListSubscriptionsByJobVacancyIdUseCaseInput(jobVacancyId, searchQuery);
    }
}
