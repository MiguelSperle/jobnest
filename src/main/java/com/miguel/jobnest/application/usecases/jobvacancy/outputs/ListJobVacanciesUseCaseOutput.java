package com.miguel.jobnest.application.usecases.jobvacancy.outputs;

import com.miguel.jobnest.domain.entities.JobVacancy;
import com.miguel.jobnest.domain.pagination.Pagination;

public record ListJobVacanciesUseCaseOutput(
        Pagination<JobVacancy> paginatedJobVacancies
) {
    public static ListJobVacanciesUseCaseOutput from(Pagination<JobVacancy> paginatedJobVacancies) {
        return new ListJobVacanciesUseCaseOutput(paginatedJobVacancies);
    }
}
