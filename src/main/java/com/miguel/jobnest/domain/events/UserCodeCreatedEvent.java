package com.miguel.jobnest.domain.events;

import com.miguel.jobnest.domain.entities.UserCode;
import com.miguel.jobnest.domain.enums.UserCodeType;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;

import java.time.LocalDateTime;

public record UserCodeCreatedEvent(
        String code,
        UserCodeType userCodeType,
        String userId,
        String eventId,
        String eventType,
        String aggregateId,
        String aggregateType,
        LocalDateTime createdAt
) implements DomainEvent {
    public UserCodeCreatedEvent(
            String code,
            UserCodeType userCodeType,
            String userId,
            String aggregateId
    ) {
        this(
                code,
                userCodeType,
                userId,
                IdentifierUtils.generateNewId(),
                "UserCodeCreatedEvent",
                aggregateId,
                UserCode.class.getSimpleName(),
                TimeUtils.now()
        );
    }
}
