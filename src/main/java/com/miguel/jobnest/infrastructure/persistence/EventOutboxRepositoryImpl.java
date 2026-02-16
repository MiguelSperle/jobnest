package com.miguel.jobnest.infrastructure.persistence;

import com.miguel.jobnest.application.abstractions.repositories.EventOutboxRepository;
import com.miguel.jobnest.domain.events.DomainEvent;
import com.miguel.jobnest.infrastructure.configurations.json.Json;
import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaEventOutboxEntity;
import com.miguel.jobnest.infrastructure.persistence.jpa.repositories.JpaEventOutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventOutboxRepositoryImpl implements EventOutboxRepository {
    private final JpaEventOutboxRepository jpaEventOutboxRepository;

    @Override
    public void save(String exchange, String routingKey, DomainEvent event) {
        final String payload = Json.writeValueAsString(event);
        this.jpaEventOutboxRepository.save(JpaEventOutboxEntity.newEventOutboxEntity(
                event.eventId(),
                payload,
                event.aggregateId(),
                event.aggregateType(),
                event.eventType(),
                exchange,
                routingKey
        ));
    }
}
