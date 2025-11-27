package com.miguel.jobnest.application.usecases.job.inputs;

import com.miguel.jobnest.domain.pagination.SearchQuery;

public record ListJobsUseCaseInput(
        SearchQuery searchQuery
) {
    public static ListJobsUseCaseInput with(SearchQuery searchQuery) {
        return new ListJobsUseCaseInput(searchQuery);
    }
}
