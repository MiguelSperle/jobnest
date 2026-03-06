package com.miguel.jobnest.domain.entities;

import com.miguel.jobnest.domain.Entity;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;

import java.time.LocalDateTime;

public class Subscription extends Entity {
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
        super(id);
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
                this.getId(),
                this.userId,
                this.jobVacancyId,
                this.resumeUrl,
                isCanceled,
                this.createdAt
        );
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
                "id='" + this.getId() + '\'' +
                ", userId='" + this.userId + '\'' +
                ", jobVacancyId='" + this.jobVacancyId + '\'' +
                ", resumeUrl='" + this.resumeUrl + '\'' +
                ", isCanceled=" + this.isCanceled +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
