package com.miguel.jobnest.infrastructure.services;

import com.miguel.jobnest.infrastructure.abstractions.services.EventBusService;
import com.miguel.jobnest.infrastructure.abstractions.resolvers.eventrouting.EventRoutingResolver;
import com.miguel.jobnest.infrastructure.exceptions.EventPublishingFailedException;
import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaEventOutboxEntity;
import com.miguel.jobnest.infrastructure.resolvers.eventrouting.EventRouting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQEventBusService implements EventBusService {
    private final RabbitTemplate rabbitTemplate;
    private final EventRoutingResolver eventRoutingResolver;

    public RabbitMQEventBusService(final RabbitTemplate rabbitTemplate, final EventRoutingResolver eventRoutingResolver) {
        this.rabbitTemplate = rabbitTemplate;
        this.eventRoutingResolver = eventRoutingResolver;
    }

    private static final Logger log = LoggerFactory.getLogger(RabbitMQEventBusService.class);

    @Override
    public void publish(JpaEventOutboxEntity jpaEventOutboxEntity) {
        try {
            final EventRouting eventRouting = this.eventRoutingResolver.resolve(jpaEventOutboxEntity.getEventType());

            this.rabbitTemplate.convertAndSend(eventRouting.exchange(), eventRouting.routingKey(), jpaEventOutboxEntity.getPayload());
        } catch (Exception ex) {
            log.error("Failed to publish event: {}", jpaEventOutboxEntity.getEventType(), ex);
            throw EventPublishingFailedException.with("Failed to publish event", ex);
        }
    }
}
