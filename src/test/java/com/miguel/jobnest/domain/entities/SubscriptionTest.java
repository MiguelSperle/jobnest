package com.miguel.jobnest.domain.entities;

import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class SubscriptionTest {

    @Test
    void shouldReturnSubscription_whenCallNewSubscription() {
        final String userId = IdentifierUtils.generateUUID();
        final String jobVacancyId = IdentifierUtils.generateUUID();
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
        final String id = IdentifierUtils.generateUUID();
        final String userId = IdentifierUtils.generateUUID();
        final String jobVacancyId = IdentifierUtils.generateUUID();
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
        final String id = IdentifierUtils.generateUUID();
        final String userId = IdentifierUtils.generateUUID();
        final String jobVacancyId = IdentifierUtils.generateUUID();
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

        final Subscription updatedSubscription = subscription.withIsCanceled(true);

        Assertions.assertEquals(true, updatedSubscription.getIsCanceled());
    }

    @Test
    void shouldReturnFormattedSubscription_whenCallToString() {
        final String id = IdentifierUtils.generateUUID();
        final String userId = IdentifierUtils.generateUUID();
        final String jobVacancyId = IdentifierUtils.generateUUID();
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

        final String expectedToString = "Subscription{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", jobVacancyId='" + jobVacancyId + '\'' +
                ", resumeUrl='" + resumeUrl + '\'' +
                ", isCanceled='" + isCanceled + '\'' +
                ", createdAt=" + createdAt +
                '}';

        Assertions.assertEquals(expectedToString, subscription.toString());
    }
}
