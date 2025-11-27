package com.miguel.jobnest.application.abstractions.repositories;

import com.miguel.jobnest.domain.entities.JobVacancy;
import com.miguel.jobnest.domain.pagination.Pagination;
import com.miguel.jobnest.domain.pagination.SearchQuery;

import java.util.Optional;

public interface JobVacancyRepository {
    JobVacancy save(JobVacancy jobVacancy);
    Pagination<JobVacancy> findAllPaginatedByUserId(String userId, SearchQuery searchQuery);
    Pagination<JobVacancy> findAllPaginated(SearchQuery searchQuery);
    Optional<JobVacancy> findById(String id);
}
