package com.miguel.jobnest.application.usecases.jobvacancy.inputs;

import com.miguel.jobnest.domain.pagination.SearchQuery;

public record ListJobVacanciesByUserIdUseCaseInput(
        String userId,
        SearchQuery searchQuery
) {
    public static ListJobVacanciesByUserIdUseCaseInput with(String userId, SearchQuery searchQuery) {
        return new ListJobVacanciesByUserIdUseCaseInput(userId, searchQuery);
    }
}
