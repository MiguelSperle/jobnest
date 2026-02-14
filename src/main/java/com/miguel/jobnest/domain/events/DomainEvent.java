package com.miguel.jobnest.domain.events;

import java.time.LocalDateTime;

public interface DomainEvent {
    String eventId();
    String eventType();
    String aggregateId();
    String aggregateType();
    LocalDateTime createdAt();
}

