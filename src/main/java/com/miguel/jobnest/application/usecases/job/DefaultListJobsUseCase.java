package com.miguel.jobnest.application.usecases.job;

import com.miguel.jobnest.application.abstractions.repositories.JobRepository;
import com.miguel.jobnest.application.abstractions.usecases.job.ListJobsUseCase;
import com.miguel.jobnest.application.usecases.job.inputs.ListJobsUseCaseInput;
import com.miguel.jobnest.application.usecases.job.outputs.ListJobsUseCaseOutput;
import com.miguel.jobnest.domain.entities.Job;
import com.miguel.jobnest.domain.pagination.Pagination;
import com.miguel.jobnest.domain.pagination.SearchQuery;

public class DefaultListJobsUseCase implements ListJobsUseCase {
    private final JobRepository jobRepository;

    public DefaultListJobsUseCase(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public ListJobsUseCaseOutput execute(ListJobsUseCaseInput input) {
        final Pagination<Job> paginatedJobs = this.getAllPaginatedJobs(input.searchQuery());

        return ListJobsUseCaseOutput.from(paginatedJobs);
    }

    private Pagination<Job> getAllPaginatedJobs(SearchQuery searchQuery) {
        return this.jobRepository.findAllPaginated(searchQuery);
    }
}
