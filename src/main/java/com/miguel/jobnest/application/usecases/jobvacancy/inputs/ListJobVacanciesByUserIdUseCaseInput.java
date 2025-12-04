package com.miguel.jobnest.application.usecases.jobvacancy.inputs;

import com.miguel.jobnest.domain.pagination.SearchQuery;

public record ListJobVacanciesByUserIdUseCaseInput(
        SearchQuery searchQuery
) {
    public static ListJobVacanciesByUserIdUseCaseInput with(SearchQuery searchQuery) {
        return new ListJobVacanciesByUserIdUseCaseInput(searchQuery);
    }
}
