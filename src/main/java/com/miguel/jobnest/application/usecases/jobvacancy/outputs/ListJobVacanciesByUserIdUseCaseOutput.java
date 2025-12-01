package com.miguel.jobnest.application.usecases.jobvacancy.outputs;

import com.miguel.jobnest.domain.entities.JobVacancy;
import com.miguel.jobnest.domain.pagination.Pagination;

public record ListJobVacanciesByUserIdUseCaseOutput(
        Pagination<JobVacancy> paginatedJobVacancies
) {
    public static ListJobVacanciesByUserIdUseCaseOutput from(Pagination<JobVacancy> paginatedJobVacancies) {
        return new ListJobVacanciesByUserIdUseCaseOutput(paginatedJobVacancies);
    }
}
