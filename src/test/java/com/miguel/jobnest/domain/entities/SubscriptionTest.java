package com.miguel.jobnest.domain.entities;

import com.miguel.jobnest.domain.builders.SubscriptionBuilder;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class SubscriptionTest {

    @Test
    void shouldReturnSubscription_whenCallNewSubscription() {
        final String userId = IdentifierUtils.generateNewId();
        final String jobVacancyId = IdentifierUtils.generateNewId();
        final String resumeUrl = "resume-url";

        final Subscription newSubscription = Subscription.newSubscription(
                userId,
                jobVacancyId,
                resumeUrl
        );

        Assertions.assertNotNull(newSubscription);
        Assertions.assertNotNull(newSubscription.getId());
        Assertions.assertEquals(userId, newSubscription.getUserId());
        Assertions.assertEquals(jobVacancyId, newSubscription.getJobVacancyId());
        Assertions.assertEquals(resumeUrl, newSubscription.getResumeUrl());
        Assertions.assertFalse(newSubscription.getIsCanceled());
        Assertions.assertNotNull(newSubscription.getCreatedAt());
    }

    @Test
    void shouldReturnSubscription_whenCallWith() {
        final String id = IdentifierUtils.generateNewId();
        final String userId = IdentifierUtils.generateNewId();
        final String jobVacancyId = IdentifierUtils.generateNewId();
        final String resumeUrl = "resume-url";
        final boolean isCanceled = false;
        final LocalDateTime createdAt = TimeUtils.now();

        final Subscription subscription = Subscription.with(
                id,
                userId,
                jobVacancyId,
                resumeUrl,
                isCanceled,
                createdAt
        );

        Assertions.assertNotNull(subscription);
        Assertions.assertEquals(id, subscription.getId());
        Assertions.assertEquals(userId, subscription.getUserId());
        Assertions.assertEquals(jobVacancyId, subscription.getJobVacancyId());
        Assertions.assertEquals(resumeUrl, subscription.getResumeUrl());
        Assertions.assertEquals(isCanceled, subscription.getIsCanceled());
        Assertions.assertEquals(createdAt, subscription.getCreatedAt());
    }

    @Test
    void shouldReturnUpdatedSubscription_whenCallWithIsCanceled() {
        final Subscription subscription = SubscriptionBuilder.subscription().isCanceled(false).build();

        final boolean newIsCanceled = true;

        final Subscription updatedSubscription = subscription.withIsCanceled(newIsCanceled);

        Assertions.assertNotNull(updatedSubscription);
        Assertions.assertEquals(newIsCanceled, updatedSubscription.getIsCanceled());
    }

    @Test
    void shouldReturnFormattedSubscription_whenCallToString() {
        final Subscription subscription = SubscriptionBuilder.subscription()
                .userId(IdentifierUtils.generateNewId())
                .jobVacancyId(IdentifierUtils.generateNewId())
                .resumeUrl("resume-url")
                .isCanceled(false)
                .createdAt(TimeUtils.now())
                .build();

        final String expectedToString = "Subscription{" +
                "id='" + subscription.getId() + '\'' +
                ", userId='" + subscription.getUserId() + '\'' +
                ", jobVacancyId='" + subscription.getJobVacancyId() + '\'' +
                ", resumeUrl='" + subscription.getResumeUrl() + '\'' +
                ", isCanceled=" + subscription.getIsCanceled() +
                ", createdAt=" + subscription.getCreatedAt() +
                '}';

        final String toStringResult = subscription.toString();

        Assertions.assertNotNull(toStringResult);
        Assertions.assertEquals(expectedToString, toStringResult);
    }
}
