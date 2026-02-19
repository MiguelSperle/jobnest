package com.miguel.jobnest.application.usecases.jobvacancy;

import com.miguel.jobnest.application.abstractions.repositories.JobVacancyRepository;
import com.miguel.jobnest.application.abstractions.services.SecurityService;
import com.miguel.jobnest.application.abstractions.usecases.jobvacancy.ListJobVacanciesByUserIdUseCase;
import com.miguel.jobnest.application.usecases.jobvacancy.inputs.ListJobVacanciesByUserIdUseCaseInput;
import com.miguel.jobnest.application.usecases.jobvacancy.outputs.ListJobVacanciesByUserIdUseCaseOutput;
import com.miguel.jobnest.domain.entities.JobVacancy;
import com.miguel.jobnest.domain.pagination.Pagination;
import com.miguel.jobnest.domain.pagination.SearchQuery;

public class DefaultListJobVacanciesByUserIdUseCase implements ListJobVacanciesByUserIdUseCase {
    private final JobVacancyRepository jobVacancyRepository;
    private final SecurityService securityService;

    public DefaultListJobVacanciesByUserIdUseCase(
            final JobVacancyRepository jobVacancyRepository,
            final SecurityService securityService
    ) {
        this.jobVacancyRepository = jobVacancyRepository;
        this.securityService = securityService;
    }

    @Override
    public ListJobVacanciesByUserIdUseCaseOutput execute(final ListJobVacanciesByUserIdUseCaseInput input) {
        final String authenticatedUserId = this.securityService.getPrincipal();

        final Pagination<JobVacancy> paginatedJobVacancies = this.getAllPaginatedJobVacanciesByUserId(authenticatedUserId, input.searchQuery());

        return ListJobVacanciesByUserIdUseCaseOutput.from(paginatedJobVacancies);
    }

    private Pagination<JobVacancy> getAllPaginatedJobVacanciesByUserId(final String userId, final SearchQuery searchQuery) {
        return this.jobVacancyRepository.findAllPaginatedByUserId(userId, searchQuery);
    }
}
