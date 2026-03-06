package com.miguel.jobnest.infrastructure.persistence;

import com.miguel.jobnest.infrastructure.abstractions.repositories.EventOutboxRepository;
import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaEventOutboxEntity;
import com.miguel.jobnest.infrastructure.persistence.jpa.repositories.JpaEventOutboxRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventOutboxRepositoryImpl implements EventOutboxRepository {
    private final JpaEventOutboxRepository jpaEventOutboxRepository;

    public EventOutboxRepositoryImpl(final JpaEventOutboxRepository jpaEventOutboxRepository) {
        this.jpaEventOutboxRepository = jpaEventOutboxRepository;
    }

    @Override
    public void save(JpaEventOutboxEntity jpaEventOutboxEntity) {
        this.jpaEventOutboxRepository.save(jpaEventOutboxEntity);
    }

    @Override
    public List<JpaEventOutboxEntity> findFirst10ByStatus(String status) {
        return this.jpaEventOutboxRepository.findFirst10ByStatus(status);
    }
}
