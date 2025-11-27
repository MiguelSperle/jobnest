package com.miguel.jobnest.application.usecases.job.outputs;

import com.miguel.jobnest.domain.entities.Job;
import com.miguel.jobnest.domain.pagination.Pagination;

public record ListJobsUseCaseOutput(
        Pagination<Job> paginatedJobs
) {
    public static ListJobsUseCaseOutput from(Pagination<Job> paginatedJobs) {
        return new ListJobsUseCaseOutput(paginatedJobs);
    }
}
