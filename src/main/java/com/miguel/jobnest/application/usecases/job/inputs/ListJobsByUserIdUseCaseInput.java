package com.miguel.jobnest.application.usecases.job.inputs;

import com.miguel.jobnest.domain.pagination.SearchQuery;

public record ListJobsByUserIdUseCaseInput(
        String userId,
        SearchQuery searchQuery
) {
    public static ListJobsByUserIdUseCaseInput with(String userId, SearchQuery searchQuery) {
        return new ListJobsByUserIdUseCaseInput(userId, searchQuery);
    }
}
