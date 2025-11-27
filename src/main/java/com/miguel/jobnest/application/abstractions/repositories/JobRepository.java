package com.miguel.jobnest.application.abstractions.repositories;

import com.miguel.jobnest.domain.entities.Job;
import com.miguel.jobnest.domain.pagination.Pagination;
import com.miguel.jobnest.domain.pagination.SearchQuery;

import java.util.Optional;

public interface JobRepository {
    Job save(Job job);
    Pagination<Job> findAllPaginatedByUserId(String userId, SearchQuery searchQuery);
    Pagination<Job> findAllPaginated(SearchQuery searchQuery);
    Optional<Job> findById(String id);
}
