package com.miguel.jobnest.application.abstractions.repositories;

import com.miguel.jobnest.domain.events.DomainEvent;

public interface EventOutboxRepository {
    void save(String exchange, String routingKey, DomainEvent event);
}
