package com.miguel.jobnest.infrastructure.persistence.jpa.entities;

import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "processed_events")
public class JpaProcessedEventEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(name = "event_id", nullable = false, length = 36)
    private String eventId;

    @Column(nullable = false, length = 100)
    private String listener;

    @Column(name = "processed_at", nullable = false)
    private LocalDateTime processedAt;

    protected JpaProcessedEventEntity() {
    }

    private JpaProcessedEventEntity(
            final String id,
            final String eventId,
            final String listener,
            final LocalDateTime processedAt
    ) {
        this.id = id;
        this.eventId = eventId;
        this.listener = listener;
        this.processedAt = processedAt;
    }

    public static JpaProcessedEventEntity newJpaProcessedEventEntity(final String eventId, final String listener) {
        return new JpaProcessedEventEntity(
                IdentifierUtils.generateNewId(),
                eventId,
                listener,
                TimeUtils.now()
        );
    }

    public String getId() {
        return this.id;
    }

    public String getEventId() {
        return this.eventId;
    }

    public String getListener() {
        return this.listener;
    }

    public LocalDateTime getProcessedAt() {
        return this.processedAt;
    }
}
