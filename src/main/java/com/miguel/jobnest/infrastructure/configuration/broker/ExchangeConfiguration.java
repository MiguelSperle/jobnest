package com.miguel.jobnest.infrastructure.configuration.broker;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangeConfiguration {
    @Bean
    public DirectExchange userCreatedExchange() {
        return new DirectExchange("user.created.exchange");
    }

    @Bean
    public DirectExchange userCreatedDlqExchange() {
        return new DirectExchange("user.created.dlq.exchange");
    }
}
