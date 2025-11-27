package com.miguel.jobnest.application.usecases.job.outputs;

import com.miguel.jobnest.domain.entities.Job;
import com.miguel.jobnest.domain.pagination.Pagination;

public record ListJobsByUserIdUseCaseOutput(
        Pagination<Job> paginatedJobs
) {
    public static ListJobsByUserIdUseCaseOutput from(Pagination<Job> paginatedJobs) {
        return new ListJobsByUserIdUseCaseOutput(paginatedJobs);
    }
}
