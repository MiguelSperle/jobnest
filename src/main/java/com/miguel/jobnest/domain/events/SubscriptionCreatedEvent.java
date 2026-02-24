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
            final String aggregateId,
            final String userId,
            final String jobVacancyId
    ) {
        this(
                userId,
                jobVacancyId,
                IdentifierUtils.generateNewId(),
                "SubscriptionCreated",
                aggregateId,
                Subscription.class.getSimpleName(),
                TimeUtils.now()

        );
    }
}

