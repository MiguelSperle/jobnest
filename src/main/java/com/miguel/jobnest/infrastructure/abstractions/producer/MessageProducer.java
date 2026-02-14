package com.miguel.jobnest.infrastructure.abstractions.producer;

import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaEventOutboxEntity;

public interface MessageProducer {
    void publish(JpaEventOutboxEntity jpaEventOutboxEntity);
}
