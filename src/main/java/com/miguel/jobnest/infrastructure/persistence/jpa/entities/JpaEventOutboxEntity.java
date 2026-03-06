package com.miguel.jobnest.infrastructure.persistence.jpa.entities;

import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;
import com.miguel.jobnest.infrastructure.enums.EventOutboxStatus;
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
    @Column(name = "status", nullable = false, length = 10)
    private EventOutboxStatus eventOutboxStatus;

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
            final EventOutboxStatus eventOutboxStatus,
            final LocalDateTime createdAt
    ) {
        this.id = id;
        this.eventId = eventId;
        this.payload = payload;
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
        this.eventType = eventType;
        this.eventOutboxStatus = eventOutboxStatus;
        this.createdAt = createdAt;
    }

    public static JpaEventOutboxEntity newJpaEventOutboxEntity(
            final String eventId,
            final byte[] payload,
            final String aggregateId,
            final String aggregateType,
            final String eventType
    ) {
        return new JpaEventOutboxEntity(
                IdentifierUtils.generateNewId(),
                eventId,
                payload,
                aggregateId,
                aggregateType,
                eventType,
                EventOutboxStatus.PENDING,
                TimeUtils.now()
        );
    }

    public JpaEventOutboxEntity withEventOutboxStatus(final EventOutboxStatus eventOutboxStatus) {
        return new JpaEventOutboxEntity(
                this.id,
                this.eventId,
                this.payload,
                this.aggregateId,
                this.aggregateType,
                this.eventType,
                eventOutboxStatus,
                this.createdAt
        );
    }

    public String getId() {
        return this.id;
    }

    public String getEventId() {
        return this.eventId;
    }

    public byte[] getPayload() {
        return this.payload;
    }

    public String getAggregateId() {
        return this.aggregateId;
    }

    public String getAggregateType() {
        return this.aggregateType;
    }

    public String getEventType() {
        return this.eventType;
    }

    public EventOutboxStatus getEventOutboxStatus() {
        return this.eventOutboxStatus;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
}
