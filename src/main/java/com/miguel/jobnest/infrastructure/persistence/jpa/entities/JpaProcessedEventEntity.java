package com.miguel.jobnest.infrastructure.persistence.jpa.entities;

import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "processed_events")
@AllArgsConstructor
@NoArgsConstructor
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

    public static JpaProcessedEventEntity newProcessedEventEntity(final String eventId, final String listener) {
        return new JpaProcessedEventEntity(
                IdentifierUtils.generateNewId(),
                eventId,
                listener,
                TimeUtils.now()
        );
    }
}
