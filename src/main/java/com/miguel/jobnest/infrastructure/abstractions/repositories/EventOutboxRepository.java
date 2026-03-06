package com.miguel.jobnest.infrastructure.abstractions.repositories;

import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaEventOutboxEntity;

import java.util.List;

public interface EventOutboxRepository {
    void save(JpaEventOutboxEntity jpaEventOutboxEntity);
    List<JpaEventOutboxEntity> findFirst10ByStatus(String status);
}
