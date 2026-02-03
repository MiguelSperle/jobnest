package com.miguel.jobnest.domain.events;

import com.miguel.jobnest.domain.utils.IdentifierUtils;

public record SubscriptionCreatedEvent(
        String eventId,
        String userId,
        String jobVacancyId
) {
    public static SubscriptionCreatedEvent from(String userId, String jobVacancyId) {
        return new SubscriptionCreatedEvent(IdentifierUtils.generateNewId(), userId, jobVacancyId);
    }
}
