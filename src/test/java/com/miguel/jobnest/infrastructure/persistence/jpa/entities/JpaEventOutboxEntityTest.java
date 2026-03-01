package com.miguel.jobnest.infrastructure.persistence.jpa.entities;

import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;
import com.miguel.jobnest.infrastructure.enums.EventOutboxStatus;
import com.miguel.jobnest.testsupport.builders.jpa.JpaEventOutboxEntityTestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class JpaEventOutboxEntityTest {
    @Test
    void shouldReturnJpaEventOutboxEntity_whenCallNewJpaEventOutboxEntity() {
        final String eventId = IdentifierUtils.generateNewId();
        final byte[] payload = new byte[0];
        final String aggregateId = IdentifierUtils.generateNewId();
        final String aggregateType = "Any aggregate type";
        final String eventType = "Any event type";
        final String exchange = "test.exchange";
        final String routingKey = "test.routing.key";

        final JpaEventOutboxEntity newJpaEventOutboxEntity = JpaEventOutboxEntity.newJpaEventOutboxEntity(
                eventId,
                payload,
                aggregateId,
                aggregateType,
                eventType,
                exchange,
                routingKey
        );

        Assertions.assertNotNull(newJpaEventOutboxEntity);
        Assertions.assertNotNull(newJpaEventOutboxEntity.getId());
        Assertions.assertEquals(eventId, newJpaEventOutboxEntity.getEventId());
        Assertions.assertEquals(payload, newJpaEventOutboxEntity.getPayload());
        Assertions.assertEquals(aggregateId, newJpaEventOutboxEntity.getAggregateId());
        Assertions.assertEquals(aggregateType, newJpaEventOutboxEntity.getAggregateType());
        Assertions.assertEquals(eventType, newJpaEventOutboxEntity.getEventType());
        Assertions.assertEquals(exchange, newJpaEventOutboxEntity.getExchange());
        Assertions.assertEquals(routingKey, newJpaEventOutboxEntity.getRoutingKey());
        Assertions.assertNotNull(newJpaEventOutboxEntity.getCreatedAt());
    }

    @Test
    void shouldReturnJpaEventOutboxEntity_whenCallWith() {
        final String id = IdentifierUtils.generateNewId();
        final String eventId = IdentifierUtils.generateNewId();
        final byte[] payload = new byte[0];
        final String aggregateId = IdentifierUtils.generateNewId();
        final String aggregateType = "Any aggregate type";
        final String eventType = "Any event type";
        final EventOutboxStatus eventOutboxStatus = EventOutboxStatus.PENDING;
        final String exchange = "test.exchange";
        final String routingKey = "test.routing.key";
        final LocalDateTime createdAt = TimeUtils.now();

        final JpaEventOutboxEntity jpaEventOutboxEntity = JpaEventOutboxEntity.with(
                id,
                eventId,
                payload,
                aggregateId,
                aggregateType,
                eventType,
                eventOutboxStatus,
                exchange,
                routingKey,
                createdAt
        );

        Assertions.assertNotNull(jpaEventOutboxEntity);
        Assertions.assertEquals(id, jpaEventOutboxEntity.getId());
        Assertions.assertEquals(eventId, jpaEventOutboxEntity.getEventId());
        Assertions.assertEquals(payload, jpaEventOutboxEntity.getPayload());
        Assertions.assertEquals(aggregateId, jpaEventOutboxEntity.getAggregateId());
        Assertions.assertEquals(aggregateType, jpaEventOutboxEntity.getAggregateType());
        Assertions.assertEquals(eventType, jpaEventOutboxEntity.getEventType());
        Assertions.assertEquals(eventOutboxStatus, jpaEventOutboxEntity.getEventOutboxStatus());
        Assertions.assertEquals(exchange, jpaEventOutboxEntity.getExchange());
        Assertions.assertEquals(routingKey, jpaEventOutboxEntity.getRoutingKey());
        Assertions.assertEquals(createdAt, jpaEventOutboxEntity.getCreatedAt());
    }

    @Test
    void shouldReturnUpdatedJpaEventOutboxEntity_whenCallWithEventOutboxStatus() {
        final JpaEventOutboxEntity jpaEventOutboxEntity = JpaEventOutboxEntityTestBuilder.aJpaEventOutboxEntity().build();

        final EventOutboxStatus newEventOutboxStatus = EventOutboxStatus.PUBLISHED;

        final JpaEventOutboxEntity updatedJpaEventOutboxEntity = jpaEventOutboxEntity.withEventOutboxStatus(newEventOutboxStatus);

        Assertions.assertNotNull(updatedJpaEventOutboxEntity);
        Assertions.assertEquals(newEventOutboxStatus, updatedJpaEventOutboxEntity.getEventOutboxStatus());
    }
}
