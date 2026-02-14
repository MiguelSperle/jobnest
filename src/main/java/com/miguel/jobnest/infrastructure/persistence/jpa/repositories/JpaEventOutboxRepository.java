package com.miguel.jobnest.infrastructure.persistence.jpa.repositories;

import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaEventOutboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaEventOutboxRepository extends JpaRepository<JpaEventOutboxEntity, String> {
    @Query(nativeQuery = true, value = "SELECT * FROM events_outbox WHERE status = :status ORDER BY created_at LIMIT 10 FOR UPDATE SKIP LOCKED")
    List<JpaEventOutboxEntity> findFirst10ByStatus(@Param("status") String status);
}
