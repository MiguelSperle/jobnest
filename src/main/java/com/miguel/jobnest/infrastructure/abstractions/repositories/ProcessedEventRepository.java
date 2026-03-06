package com.miguel.jobnest.infrastructure.abstractions.repositories;

import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaProcessedEventEntity;

public interface ProcessedEventRepository {
    boolean existsByEventIdAndListener(String eventId, String listener);
    void save(JpaProcessedEventEntity jpaProcessedEventEntity);
}
