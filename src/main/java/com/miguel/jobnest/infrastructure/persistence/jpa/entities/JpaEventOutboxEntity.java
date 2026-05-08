package com.miguel.jobnest.infrastructure.persistence.jpa.entities;

import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;
import com.miguel.jobnest.infrastructure.persistence.jpa.enums.EventOutboxStatus;
import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Table(name = "events_outbox")
@Builder
public class JpaEventOutboxEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(name = "event_id", unique = true, nullable = false, length = 36)
    private String eventId;

    @Column(columnDefinition = "BYTEA", nullable = false)
    private byte[] payload;

    @Column(name = "aggregate_id", nullable = false, length = 36)
    private String aggregateId;

    @Column(name = "aggregate_type", nullable = false, length = 40)
    private String aggregateType;

    @Column(name = "event_type", nullable = false, length = 100)
    private String eventType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private EventOutboxStatus status;

    @Column(nullable = false, length = 100)
    private String exchange;

    @Column(name = "routing_key", nullable = false, length = 100)
    private String routingKey;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected JpaEventOutboxEntity() {
    }

    private JpaEventOutboxEntity(
            final String id,
            final String eventId,
            final byte[] payload,
            final String aggregateId,
            final String aggregateType,
            final String eventType,
            final EventOutboxStatus status,
            final String exchange,
            final String routingKey,
            final LocalDateTime createdAt
    ) {
        this.id = id;
        this.eventId = eventId;
        this.payload = payload;
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
        this.eventType = eventType;
        this.status = status;
        this.exchange = exchange;
        this.routingKey = routingKey;
        this.createdAt = createdAt;
    }

    public static JpaEventOutboxEntity newEventOutbox(
            final String eventId,
            final byte[] payload,
            final String aggregateId,
            final String aggregateType,
            final String eventType,
            final String exchange,
            final String routingKey
    ) {
        return new JpaEventOutboxEntity(
                IdentifierUtils.generateNewId(),
                eventId,
                payload,
                aggregateId,
                aggregateType,
                eventType,
                EventOutboxStatus.PENDING,
                exchange,
                routingKey,
                TimeUtils.now()
        );
    }

    public String getEventId() {
        return this.eventId;
    }

    public byte[] getPayload() {
        return this.payload;
    }

    public String getEventType() {
        return this.eventType;
    }

    public EventOutboxStatus getStatus() {
        return this.status;
    }

    public void setStatus(final EventOutboxStatus status) {
        this.status = status;
    }

    public String getExchange() {
        return this.exchange;
    }

    public String getRoutingKey() {
        return this.routingKey;
    }
}
