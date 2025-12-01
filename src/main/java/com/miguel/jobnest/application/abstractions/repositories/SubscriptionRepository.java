package com.miguel.jobnest.application.abstractions.repositories;

import com.miguel.jobnest.domain.entities.Subscription;
import com.miguel.jobnest.domain.pagination.Pagination;
import com.miguel.jobnest.domain.pagination.SearchQuery;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository {
    boolean existsByUserIdAndJobVacancyId(String userId, String jobVacancyId);
    Subscription save(Subscription subscription);
    Optional<Subscription> findById(String id);
    Pagination<Subscription> findAllPaginatedByUserId(String userId, SearchQuery searchQuery);
    List<Subscription> findAllByJobVacancyId(String jobVacancyId);
}
