package com.miguel.jobnest.domain;

import com.miguel.jobnest.domain.events.DomainEvent;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class EntityTest {
    @Test
    void shouldCreateEntity_whenCallConstructor() {
        final String id = IdentifierUtils.generateNewId();
        final Entity entity = this.createEntity(id);

        Assertions.assertNotNull(entity);
        Assertions.assertEquals(id, entity.getId());
    }

    @Test
    void shouldRegisterAnDomainEvent_whenCallRegisterEvent() {
        final Entity entity = this.createEntity(IdentifierUtils.generateNewId());
        final EntityCreatedEvent event = new EntityCreatedEvent(entity.getId());

        entity.registerEvent(event);

        Assertions.assertEquals(1, entity.getDomainEvents().size());
        Assertions.assertEquals(event, entity.getDomainEvents().getFirst());
    }

    @Test
    void shouldNotRegisterEvent_whenDomainEventIsNull() {
        final Entity entity = this.createEntity(IdentifierUtils.generateNewId());

        entity.registerEvent(null);

        Assertions.assertEquals(0, entity.getDomainEvents().size());
    }

    @Test
    void shouldTestEntityEqualityAndHashcode() {
        final String idForTwoFirstEntities = IdentifierUtils.generateNewId();
        final String idForThirdEntity = IdentifierUtils.generateNewId();

        final Entity entity1 = this.createEntity(idForTwoFirstEntities);
        final Entity entity2 = this.createEntity(idForTwoFirstEntities);
        final Entity entity3 = this.createEntity(idForThirdEntity);

        // equality test
        Assertions.assertEquals(entity1, entity2);
        Assertions.assertNotEquals(entity1, entity3);

        // hashCode test
        Assertions.assertEquals(entity1.hashCode(), entity2.hashCode());
        Assertions.assertNotEquals(entity1.hashCode(), entity3.hashCode());
    }

    public record EntityCreatedEvent(
            String eventId,
            String eventType,
            String aggregateId,
            String aggregateType,
            LocalDateTime createdAt
    ) implements DomainEvent {
        public EntityCreatedEvent(String aggregateId) {
            this(
                    IdentifierUtils.generateNewId(),
                    "EntityCreated",
                    aggregateId,
                    "Entity",
                    TimeUtils.now()
            );
        }
    }

    private Entity createEntity(String id) {
        return new Entity(id) {
        };
    }
}
