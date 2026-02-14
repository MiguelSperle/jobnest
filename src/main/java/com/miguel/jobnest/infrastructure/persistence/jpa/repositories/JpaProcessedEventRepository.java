package com.miguel.jobnest.infrastructure.persistence.jpa.repositories;

import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaProcessedEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaProcessedEventRepository extends JpaRepository<JpaProcessedEventEntity, String> {
    @Query(nativeQuery = true, value = "SELECT EXISTS (SELECT 1 FROM processed_events WHERE event_id = :eventId AND consumed_by = :consumedBy)")
    boolean existsByEventIdAndConsumedBy(@Param("eventId") String eventId, @Param("consumedBy") String consumedBy);
}
