package com.miguel.jobnest.infrastructure.producer;

import com.miguel.jobnest.infrastructure.abstractions.producer.MessageProducer;
import com.miguel.jobnest.infrastructure.exceptions.EventPublishingFailedException;
import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaEventOutboxEntity;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMQProducerImpl implements MessageProducer {
    private final RabbitTemplate rabbitTemplate;

    private static final Logger log = LoggerFactory.getLogger(RabbitMQProducerImpl.class);

    @Override
    public void publish(JpaEventOutboxEntity eventOutboxEntity) {
        try {
            this.rabbitTemplate.convertAndSend(eventOutboxEntity.getExchange(), eventOutboxEntity.getRoutingKey(), eventOutboxEntity.getPayload());
        } catch (Exception ex) {
            log.error("Failed to publish event: {}", eventOutboxEntity.getEventType(), ex);
            throw EventPublishingFailedException.with("Failed to publish event", ex);
        }
    }
}
