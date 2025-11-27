package com.miguel.jobnest.application.usecases.job;

import com.miguel.jobnest.application.abstractions.repositories.JobRepository;
import com.miguel.jobnest.application.abstractions.usecases.job.ListJobsByUserIdUseCase;
import com.miguel.jobnest.application.usecases.job.inputs.ListJobsByUserIdUseCaseInput;
import com.miguel.jobnest.application.usecases.job.outputs.ListJobsByUserIdUseCaseOutput;
import com.miguel.jobnest.domain.entities.Job;
import com.miguel.jobnest.domain.pagination.Pagination;
import com.miguel.jobnest.domain.pagination.SearchQuery;

public class DefaultListJobsByUserIdUseCase implements ListJobsByUserIdUseCase {
    private final JobRepository jobRepository;

    public DefaultListJobsByUserIdUseCase(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public ListJobsByUserIdUseCaseOutput execute(ListJobsByUserIdUseCaseInput input) {
        final Pagination<Job> paginatedJobs = this.getAllPaginatedJobsByUserId(input.userId(), input.searchQuery());

        return ListJobsByUserIdUseCaseOutput.from(paginatedJobs);
    }

    private Pagination<Job> getAllPaginatedJobsByUserId(String userId, SearchQuery searchQuery) {
        return this.jobRepository.findAllPaginatedByUserId(userId, searchQuery);
    }
}
