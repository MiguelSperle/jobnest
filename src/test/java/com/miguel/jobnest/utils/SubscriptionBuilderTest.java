package com.miguel.jobnest.utils;

import com.miguel.jobnest.domain.entities.Subscription;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;

public class SubscriptionBuilderTest {
    public static Subscription build(String userId, String jobVacancyId) {
        return Subscription.with(
                IdentifierUtils.generateUUID(),
                userId,
                jobVacancyId,
                "resume-url",
                false,
                TimeUtils.now()
        );
    }
}
