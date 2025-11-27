package com.miguel.jobnest.domain.events;

public record SubscriptionCreatedEvent(
        String userId,
        String jobVacancyId
) {
    public static SubscriptionCreatedEvent from(String userId, String jobVacancyId) {
        return new SubscriptionCreatedEvent(userId, jobVacancyId);
    }
}
