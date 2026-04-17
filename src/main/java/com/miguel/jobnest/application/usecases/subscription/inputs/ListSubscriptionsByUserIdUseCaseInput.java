package com.miguel.jobnest.application.usecases.subscription.inputs;

import com.miguel.jobnest.domain.pagination.SearchQuery;

public record ListSubscriptionsByUserIdUseCaseInput(
        String userId,
        SearchQuery searchQuery
) {
    public static ListSubscriptionsByUserIdUseCaseInput with(final String userId, final SearchQuery searchQuery) {
        return new ListSubscriptionsByUserIdUseCaseInput(userId, searchQuery);
    }
}
