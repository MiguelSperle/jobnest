package com.miguel.jobnest.infrastructure.persistence.jpa.entities;

import com.miguel.jobnest.domain.entities.Subscription;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;
import com.miguel.jobnest.testsupport.builders.entities.domain.SubscriptionTestBuilder;
import com.miguel.jobnest.testsupport.builders.entities.jpa.JpaSubscriptionEntityTestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class JpaSubscriptionEntityTest {
    @Test
    void shouldReturnJpaSubscriptionEntity_whenCallToEntity() {
        final Subscription subscription = SubscriptionTestBuilder.aSubscription().userId(IdentifierUtils.generateNewId()).jobVacancyId(IdentifierUtils.generateNewId()).build();

        final JpaSubscriptionEntity transformedIntoJpaEntity = JpaSubscriptionEntity.toEntity(subscription);

        Assertions.assertNotNull(transformedIntoJpaEntity);
        Assertions.assertEquals(subscription.getId(), transformedIntoJpaEntity.getId());
        Assertions.assertEquals(subscription.getUserId(), transformedIntoJpaEntity.getUserId());
        Assertions.assertEquals(subscription.getJobVacancyId(), transformedIntoJpaEntity.getJobVacancyId());
        Assertions.assertEquals(subscription.getResumeUrl(), transformedIntoJpaEntity.getResumeUrl());
        Assertions.assertEquals(subscription.getIsCanceled(), transformedIntoJpaEntity.getIsCanceled());
        Assertions.assertEquals(subscription.getCreatedAt(), transformedIntoJpaEntity.getCreatedAt());
    }

    @Test
    void shouldReturnSubscription_whenCallToDomain() {
        final JpaSubscriptionEntity jpaSubscriptionEntity = JpaSubscriptionEntityTestBuilder.aJpaSubscriptionEntity().build();

        final Subscription transformedIntoDomainEntity = jpaSubscriptionEntity.toDomain();

        Assertions.assertNotNull(transformedIntoDomainEntity);
        Assertions.assertEquals(jpaSubscriptionEntity.getId(), transformedIntoDomainEntity.getId());
        Assertions.assertEquals(jpaSubscriptionEntity.getUserId(), transformedIntoDomainEntity.getUserId());
        Assertions.assertEquals(jpaSubscriptionEntity.getJobVacancyId(), transformedIntoDomainEntity.getJobVacancyId());
        Assertions.assertEquals(jpaSubscriptionEntity.getResumeUrl(), transformedIntoDomainEntity.getResumeUrl());
        Assertions.assertEquals(jpaSubscriptionEntity.getIsCanceled(), transformedIntoDomainEntity.getIsCanceled());
        Assertions.assertEquals(jpaSubscriptionEntity.getCreatedAt(), transformedIntoDomainEntity.getCreatedAt());
    }

    @Test
    void shouldReturnJpaSubscriptionEntity_whenCallWith() {
        final String id = IdentifierUtils.generateNewId();
        final String userId = IdentifierUtils.generateNewId();
        final String jobVacancyId = IdentifierUtils.generateNewId();
        final String resumeUrl = "resume-url";
        final boolean isCanceled = false;
        final LocalDateTime createdAt = TimeUtils.now();

        final JpaSubscriptionEntity jpaSubscriptionEntity = JpaSubscriptionEntity.with(
                id,
                userId,
                jobVacancyId,
                resumeUrl,
                isCanceled,
                createdAt
        );

        Assertions.assertNotNull(jpaSubscriptionEntity);
        Assertions.assertEquals(id, jpaSubscriptionEntity.getId());
        Assertions.assertEquals(userId, jpaSubscriptionEntity.getUserId());
        Assertions.assertEquals(jobVacancyId, jpaSubscriptionEntity.getJobVacancyId());
        Assertions.assertEquals(resumeUrl, jpaSubscriptionEntity.getResumeUrl());
        Assertions.assertEquals(isCanceled, jpaSubscriptionEntity.getIsCanceled());
        Assertions.assertEquals(createdAt, jpaSubscriptionEntity.getCreatedAt());
    }
}
