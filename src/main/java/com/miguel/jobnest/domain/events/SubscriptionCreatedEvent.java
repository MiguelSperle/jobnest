package com.miguel.jobnest.domain.events;

import com.miguel.jobnest.domain.entities.Subscription;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;

import java.time.LocalDateTime;

public record SubscriptionCreatedEvent(
        String userId,
        String jobVacancyId,
        String eventId,
        String eventType,
        String aggregateId,
        String aggregateType,
        LocalDateTime createdAt
) implements DomainEvent {
    public SubscriptionCreatedEvent(
            String userId,
            String jobVacancyId,
            String aggregateId
    ) {
        this(
                userId,
                jobVacancyId,
                IdentifierUtils.generateNewId(),
                "SubscriptionCreatedEvent",
                aggregateId,
                Subscription.class.getSimpleName(),
                TimeUtils.now()

        );
    }
}

