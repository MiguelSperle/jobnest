package com.miguel.jobnest.infrastructure.persistence.jpa.repositories;

import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaSubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaSubscriptionRepository extends JpaRepository<JpaSubscriptionEntity, String>, JpaSpecificationExecutor<JpaSubscriptionEntity> {
    @Query(nativeQuery = true, value = "SELECT EXISTS (SELECT 1 FROM subscriptions s WHERE s.user_id = :userId AND s.job_vacancy_id = :jobVacancyId)")
    boolean existsByUserIdAndJobVacancyId(@Param("userId") String userId, @Param("jobVacancyId") String jobVacancyId);

    @Query(nativeQuery = true, value = "SELECT * FROM subscriptions s WHERE s.job_vacancy_id = :jobVacancyId")
    List<JpaSubscriptionEntity> findAllByJobVacancyId(@Param("jobVacancyId") String jobVacancyId);
}
