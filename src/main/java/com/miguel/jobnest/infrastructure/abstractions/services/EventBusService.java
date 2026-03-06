package com.miguel.jobnest.infrastructure.abstractions.services;

import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaEventOutboxEntity;

public interface EventBusService {
    void publish(JpaEventOutboxEntity jpaEventOutboxEntity);
}
