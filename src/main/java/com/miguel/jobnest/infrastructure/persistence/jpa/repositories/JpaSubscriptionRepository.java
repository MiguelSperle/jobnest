package com.miguel.jobnest.infrastructure.persistence.jpa.repositories;

import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaSubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaSubscriptionRepository extends JpaRepository<JpaSubscriptionEntity, String> {
    @Query(nativeQuery = true, value = "SELECT EXISTS (SELECT 1 FROM subscriptions s WHERE s.user_id = :userId AND s.job_id = :jobId)")
    boolean existsByUserIdAndJobId(@Param("userId") String userId, @Param("jobId") String jobId);
}
