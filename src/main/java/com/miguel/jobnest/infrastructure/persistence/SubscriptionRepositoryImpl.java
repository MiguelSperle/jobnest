package com.miguel.jobnest.infrastructure.persistence;

import com.miguel.jobnest.application.abstractions.repositories.SubscriptionRepository;
import com.miguel.jobnest.domain.entities.Subscription;
import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaSubscriptionEntity;
import com.miguel.jobnest.infrastructure.persistence.jpa.repositories.JpaSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SubscriptionRepositoryImpl implements SubscriptionRepository {
    private final JpaSubscriptionRepository jpaSubscriptionRepository;

    @Override
    public boolean existsByUserIdAndJobVacancyId(String userId, String jobVacancyId) {
        return this.jpaSubscriptionRepository.existsByUserIdAndJobVacancyId(userId, jobVacancyId);
    }

    @Override
    public Subscription save(Subscription subscription) {
        return this.jpaSubscriptionRepository.save(JpaSubscriptionEntity.from(subscription)).toEntity();
    }

    @Override
    public Optional<Subscription> findById(String id) {
        return this.jpaSubscriptionRepository.findById(id).map(JpaSubscriptionEntity::toEntity);
    }
}
