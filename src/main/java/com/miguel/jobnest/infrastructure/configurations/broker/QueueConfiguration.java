package com.miguel.jobnest.infrastructure.configurations.broker;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfiguration {
    @Bean
    public Queue userCodeCreatedQueue() {
        return QueueBuilder.durable("user.code.created.queue")
                .deadLetterExchange("user.code.created.dlq.exchange")
                .deadLetterRoutingKey("user.code.created.dlq.routing.key")
                .build();
    }

    @Bean
    public Queue userCodeCreatedDlqQueue() {
        return QueueBuilder.durable("user.code.created.dlq.queue").build();
    }

    @Bean
    public Queue subscriptionCreatedQueue() {
        return QueueBuilder.durable("subscription.created.queue")
                .deadLetterExchange("subscription.created.dlq.exchange")
                .deadLetterRoutingKey("subscription.created.dlq.routing.key")
                .build();
    }

    @Bean
    public Queue subscriptionCreatedDlqQueue() {
        return QueueBuilder.durable("subscription.created.dlq.queue").build();
    }
}
