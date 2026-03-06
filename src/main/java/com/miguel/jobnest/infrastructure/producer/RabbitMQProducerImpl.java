package com.miguel.jobnest.infrastructure.producer;

import com.miguel.jobnest.infrastructure.abstractions.producer.MessageProducer;
import com.miguel.jobnest.infrastructure.configurations.properties.rabbitmq.RabbitMQProperties;
import com.miguel.jobnest.infrastructure.exceptions.EventPublishingFailedException;
import com.miguel.jobnest.infrastructure.exceptions.MissingMessageConfigurationException;
import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaEventOutboxEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQProducerImpl implements MessageProducer {
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQProperties rabbitMQProperties;

    public RabbitMQProducerImpl(final RabbitTemplate rabbitTemplate, final RabbitMQProperties rabbitMQProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQProperties = rabbitMQProperties;
    }

    private static final Logger log = LoggerFactory.getLogger(RabbitMQProducerImpl.class);

    @Override
    public void publish(JpaEventOutboxEntity jpaEventOutboxEntity) {
        try {
            final String eventType = jpaEventOutboxEntity.getEventType();

            final RabbitMQProperties.MessageConfiguration messageConfiguration =
                    this.rabbitMQProperties.getMessageConfigurations().get(eventType);

            if (messageConfiguration == null) {
                throw MissingMessageConfigurationException.with("Missing message configuration for event type: " + eventType);
            }

            final String exchange = messageConfiguration.getExchange().getName();
            final String routingKey = messageConfiguration.getRoutingKey();

            this.rabbitTemplate.convertAndSend(exchange, routingKey, jpaEventOutboxEntity.getPayload());
        } catch (Exception ex) {
            log.error("Failed to publish event: {}", jpaEventOutboxEntity.getEventType(), ex);
            throw EventPublishingFailedException.with("Failed to publish event", ex);
        }
    }
}
