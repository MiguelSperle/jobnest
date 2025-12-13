package com.miguel.jobnest.utils;

import com.miguel.jobnest.domain.entities.Subscription;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;

import java.util.Objects;

public class SubscriptionTestBuilder {
    private String userId;
    private String jobVacancyId;

    private SubscriptionTestBuilder() {
    }

    public static SubscriptionTestBuilder aSubscription() {
        return new SubscriptionTestBuilder();
    }

    public SubscriptionTestBuilder userId(String userId) {
        this.userId = userId;
        return this;
    }

    public SubscriptionTestBuilder jobVacancyId(String jobVacancyId) {
        this.jobVacancyId = jobVacancyId;
        return this;
    }

    public Subscription build() {
        Objects.requireNonNull(this.userId, "UserId must not be null");
        Objects.requireNonNull(this.jobVacancyId, "JobVacancyId must not be null");

        return Subscription.with(
                IdentifierUtils.generateUUID(),
                this.userId,
                this.jobVacancyId,
                "resume-url",
                false,
                TimeUtils.now()
        );
    }
}
