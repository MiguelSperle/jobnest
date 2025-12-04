package com.miguel.jobnest.application.usecases.subscription.inputs;

import com.miguel.jobnest.domain.pagination.SearchQuery;

public record ListSubscriptionsByUserIdUseCaseInput(
        SearchQuery searchQuery
) {
    public static ListSubscriptionsByUserIdUseCaseInput with(SearchQuery searchQuery) {
        return new ListSubscriptionsByUserIdUseCaseInput(searchQuery);
    }
}
