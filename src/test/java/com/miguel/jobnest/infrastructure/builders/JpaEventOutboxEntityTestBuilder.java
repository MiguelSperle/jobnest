package com.miguel.jobnest.infrastructure.builders;

import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;
import com.miguel.jobnest.infrastructure.enums.EventOutboxStatus;
import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaEventOutboxEntity;

public class JpaEventOutboxEntityTestBuilder {

    private JpaEventOutboxEntityTestBuilder() {
    }

    public static JpaEventOutboxEntityTestBuilder aEventOutboxEntity() {
        return new JpaEventOutboxEntityTestBuilder();
    }

    public JpaEventOutboxEntity build() {
        return JpaEventOutboxEntity.with(
                IdentifierUtils.generateNewId(),
                IdentifierUtils.generateNewId(),
                new byte[0],
                IdentifierUtils.generateNewId(),
                "Any aggregate type",
                "Any event type",
                EventOutboxStatus.PENDING,
                "test.exchange",
                "test.routing.key",
                TimeUtils.now()
        );
    }
}
