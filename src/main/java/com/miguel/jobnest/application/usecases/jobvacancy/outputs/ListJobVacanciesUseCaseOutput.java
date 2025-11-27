package com.miguel.jobnest.application.usecases.jobvacancy.outputs;

import com.miguel.jobnest.domain.entities.JobVacancy;
import com.miguel.jobnest.domain.pagination.Pagination;

public record ListJobVacanciesUseCaseOutput(
        Pagination<JobVacancy> paginatedJobs
) {
    public static ListJobVacanciesUseCaseOutput from(Pagination<JobVacancy> paginatedJobs) {
        return new ListJobVacanciesUseCaseOutput(paginatedJobs);
    }
}
