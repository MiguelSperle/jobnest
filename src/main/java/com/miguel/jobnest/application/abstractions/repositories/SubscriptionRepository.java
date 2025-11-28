package com.miguel.jobnest.application.abstractions.repositories;

import com.miguel.jobnest.domain.entities.Subscription;

import java.util.Optional;

public interface SubscriptionRepository {
    boolean existsByUserIdAndJobVacancyId(String userId, String jobVacancyId);
    Subscription save(Subscription subscription);
    Optional<Subscription> findById(String id);
}
