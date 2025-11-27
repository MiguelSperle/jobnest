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

    @Bean
    public DirectExchange userCodeCreatedExchange() {
        return new DirectExchange("user.code.created.exchange");
    }

    @Bean
    public DirectExchange userCodeCreatedDlqExchange() {
        return new DirectExchange("user.code.created.dlq.exchange");
    }

    @Bean
    public DirectExchange subscriptionCreatedExchange() { return new DirectExchange("subscription.created.exchange");}

    @Bean
    public DirectExchange subscriptionCreatedDlqExchange() { return new DirectExchange("subscription.created.dlq.exchange"); }
}
