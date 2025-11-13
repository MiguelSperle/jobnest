package com.miguel.jobnest.infrastructure.configuration.broker;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfiguration {
    @Bean
    public Queue userCreatedQueue() {
        return QueueBuilder.durable("user.created.queue")
                .deadLetterExchange("user.created.dlq.exchange")
                .deadLetterRoutingKey("user.created.dlq.routing.key")
                .build();
    }

    @Bean
    public Queue userCreatedDlqQueue() {
        return QueueBuilder.durable("user.created.dlq.queue").build();
    }

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
}
