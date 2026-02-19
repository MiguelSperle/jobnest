package com.miguel.jobnest.infrastructure.persistence.jpa.entities;

import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;
import com.miguel.jobnest.infrastructure.enums.EventOutboxStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "events_outbox")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class JpaEventOutboxEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(name = "event_id", unique = true, nullable = false, length = 36)
    private String eventId;

    @Lob
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

    @Column(nullable = false)
    private String exchange;

    @Column(name = "routing_key", nullable = false, length = 100)
    private String routingKey;

    @Column(name = "created_at", nullable = false, length = 100)
    private LocalDateTime createdAt;

    public static JpaEventOutboxEntity newEventOutboxEntity(
            String eventId,
            byte[] payload,
            String aggregateId,
            String aggregateType,
            String eventType,
            String exchange,
            String routingKey
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

    public JpaEventOutboxEntity withEventOutboxStatus(EventOutboxStatus eventOutboxStatus) {
        return new JpaEventOutboxEntity(
                this.id,
                this.eventId,
                this.payload,
                this.aggregateId,
                this.aggregateType,
                this.eventType,
                eventOutboxStatus,
                this.exchange,
                this.routingKey,
                this.createdAt
        );
    }
}
