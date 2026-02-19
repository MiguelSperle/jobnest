package com.miguel.jobnest.application.usecases.jobvacancy.inputs;

import com.miguel.jobnest.domain.pagination.SearchQuery;

public record ListJobVacanciesUseCaseInput(
        SearchQuery searchQuery
) {
    public static ListJobVacanciesUseCaseInput with(final SearchQuery searchQuery) {
        return new ListJobVacanciesUseCaseInput(searchQuery);
    }
}
