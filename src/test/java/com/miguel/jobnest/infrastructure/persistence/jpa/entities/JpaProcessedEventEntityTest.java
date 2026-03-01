package com.miguel.jobnest.infrastructure.persistence.jpa.entities;

import com.miguel.jobnest.domain.utils.IdentifierUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JpaProcessedEventEntityTest {
    @Test
    void shouldReturnJpaProcessedEventEntity_whenCallNewJpaProcessedEventEntity() {
        final String eventId = IdentifierUtils.generateNewId();
        final String listener = "TestListener";

        final JpaProcessedEventEntity newJpaProcessedEventEntity = JpaProcessedEventEntity.newJpaProcessedEventEntity(eventId, listener);

        Assertions.assertNotNull(newJpaProcessedEventEntity);
        Assertions.assertNotNull(newJpaProcessedEventEntity.getId());
        Assertions.assertEquals(eventId, newJpaProcessedEventEntity.getEventId());
        Assertions.assertEquals(listener, newJpaProcessedEventEntity.getListener());
        Assertions.assertNotNull(newJpaProcessedEventEntity.getProcessedAt());
    }
}
