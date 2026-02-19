package com.miguel.jobnest.domain.entities;

import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;

import java.time.LocalDateTime;

public class Subscription {
    private final String id;
    private final String userId;
    private final String jobVacancyId;
    private final String resumeUrl;
    private final Boolean isCanceled;
    private final LocalDateTime createdAt;

    private Subscription(
            final String id,
            final String userId,
            final String jobVacancyId,
            final String resumeUrl,
            final Boolean isCanceled,
            final LocalDateTime createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.jobVacancyId = jobVacancyId;
        this.resumeUrl = resumeUrl;
        this.isCanceled = isCanceled;
        this.createdAt = createdAt;
    }

    public static Subscription newSubscription(
            final String userId,
            final String jobVacancyId,
            final String resumeUrl
    ) {
        return new Subscription(
                IdentifierUtils.generateNewId(),
                userId,
                jobVacancyId,
                resumeUrl,
                false,
                TimeUtils.now()
        );
    }

    public static Subscription with(
            final String id,
            final String userId,
            final String jobVacancyId,
            final String resumeUrl,
            final Boolean isCanceled,
            final LocalDateTime createdAt
    ) {
        return new Subscription(
                id,
                userId,
                jobVacancyId,
                resumeUrl,
                isCanceled,
                createdAt
        );
    }

    public Subscription withIsCanceled(final Boolean isCanceled) {
        return new Subscription(
                this.id,
                this.userId,
                this.jobVacancyId,
                this.resumeUrl,
                isCanceled,
                this.createdAt
        );
    }

    public String getId() {
        return this.id;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getJobVacancyId() {
        return this.jobVacancyId;
    }

    public String getResumeUrl() {
        return this.resumeUrl;
    }

    public Boolean getIsCanceled() {
        return this.isCanceled;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id='" + this.id + '\'' +
                ", userId='" + this.userId + '\'' +
                ", jobVacancyId='" + this.jobVacancyId + '\'' +
                ", resumeUrl='" + this.resumeUrl + '\'' +
                ", isCanceled='" + this.isCanceled + '\'' +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
