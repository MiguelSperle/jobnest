package com.miguel.jobnest.infrastructure.persistence;

import com.miguel.jobnest.infrastructure.abstractions.repositories.ProcessedEventRepository;
import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaProcessedEventEntity;
import com.miguel.jobnest.infrastructure.persistence.jpa.repositories.JpaProcessedEventRepository;
import org.springframework.stereotype.Component;

@Component
public class ProcessedEventRepositoryImpl implements ProcessedEventRepository {
    private final JpaProcessedEventRepository jpaProcessedEventRepository;

    public ProcessedEventRepositoryImpl(final JpaProcessedEventRepository jpaProcessedEventRepository) {
        this.jpaProcessedEventRepository = jpaProcessedEventRepository;
    }

    @Override
    public boolean existsByEventIdAndListener(String eventId, String listener) {
        return this.jpaProcessedEventRepository.existsByEventIdAndListener(eventId, listener);
    }

    @Override
    public void save(JpaProcessedEventEntity jpaProcessedEventEntity) {
        this.jpaProcessedEventRepository.save(jpaProcessedEventEntity);
    }
}
