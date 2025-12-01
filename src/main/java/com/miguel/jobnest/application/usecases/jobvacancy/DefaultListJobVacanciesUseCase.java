package com.miguel.jobnest.application.usecases.jobvacancy;

import com.miguel.jobnest.application.abstractions.repositories.JobVacancyRepository;
import com.miguel.jobnest.application.abstractions.usecases.jobvacancy.ListJobVacanciesUseCase;
import com.miguel.jobnest.application.usecases.jobvacancy.inputs.ListJobVacanciesUseCaseInput;
import com.miguel.jobnest.application.usecases.jobvacancy.outputs.ListJobVacanciesUseCaseOutput;
import com.miguel.jobnest.domain.entities.JobVacancy;
import com.miguel.jobnest.domain.pagination.Pagination;
import com.miguel.jobnest.domain.pagination.SearchQuery;

public class DefaultListJobVacanciesUseCase implements ListJobVacanciesUseCase {
    private final JobVacancyRepository jobVacancyRepository;

    public DefaultListJobVacanciesUseCase(JobVacancyRepository jobVacancyRepository) {
        this.jobVacancyRepository = jobVacancyRepository;
    }

    @Override
    public ListJobVacanciesUseCaseOutput execute(ListJobVacanciesUseCaseInput input) {
        final Pagination<JobVacancy> paginatedJobVacancies = this.getAllPaginatedJobVacancies(input.searchQuery());

        return ListJobVacanciesUseCaseOutput.from(paginatedJobVacancies);
    }

    private Pagination<JobVacancy> getAllPaginatedJobVacancies(SearchQuery searchQuery) {
        return this.jobVacancyRepository.findAllPaginated(searchQuery);
    }
}
