package com.miguel.jobnest.domain.builders;

import com.miguel.jobnest.domain.entities.Subscription;

import java.time.LocalDateTime;

public class SubscriptionBuilder {
    private String id;
    private String userId;
    private String jobVacancyId;
    private String resumeUrl;
    private Boolean isCanceled;
    private LocalDateTime createdAt;

    public static SubscriptionBuilder subscription() {
        return new SubscriptionBuilder();
    }

    public SubscriptionBuilder id(String id) {
        this.id = id;
        return this;
    }

    public SubscriptionBuilder userId(String userId) {
        this.userId = userId;
        return this;
    }

    public SubscriptionBuilder jobVacancyId(String jobVacancyId) {
        this.jobVacancyId = jobVacancyId;
        return this;
    }

    public SubscriptionBuilder resumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
        return this;
    }

    public SubscriptionBuilder isCanceled(boolean isCanceled) {
        this.isCanceled = isCanceled;
        return this;
    }

    public SubscriptionBuilder createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Subscription build() {
        return Subscription.with(
                this.id,
                this.userId,
                this.jobVacancyId,
                this.resumeUrl,
                this.isCanceled,
                this.createdAt
        );
    }
}
