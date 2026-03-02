package com.miguel.jobnest.infrastructure;

import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaEventOutboxEntity;

public class Fixture {
    private Fixture() {
    }

    /* ========================= JpaEventOutboxEntity ========================= */

    public static class JpaEventOutboxEntityFixture {
        private JpaEventOutboxEntityFixture() {
        }

        public static JpaEventOutboxEntity newJpaEventOutboxEntity() {
            return JpaEventOutboxEntity.newJpaEventOutboxEntity(
                    IdentifierUtils.generateNewId(),
                    new byte[0],
                    IdentifierUtils.generateNewId(),
                    "Any aggregate type",
                    "Any event type",
                    "test.exchange",
                    "test.routing.key"
            );
        }
    }
}
