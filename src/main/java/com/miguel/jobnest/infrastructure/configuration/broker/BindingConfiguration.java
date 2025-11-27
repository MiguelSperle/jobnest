package com.miguel.jobnest.infrastructure.configuration.broker;


import com.miguel.jobnest.infrastructure.configuration.broker.annotations.exchanges.*;
import com.miguel.jobnest.infrastructure.configuration.broker.annotations.queues.*;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BindingConfiguration {
    @Bean
    public Binding userCreatedBinding(
            @UserCreatedQueue Queue queue,
            @UserCreatedExchange DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("user.created.routing.key");
    }

    @Bean
    public Binding userCreatedDlqBinding(
            @UserCreatedDlqQueue Queue queue,
            @UserCreatedDlqExchange DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("user.created.dlq.routing.key");
    }

    @Bean
    public Binding userCodeCreatedBinding(
            @UserCodeCreatedQueue Queue queue,
            @UserCodeCreatedExchange DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("user.code.created.routing.key");
    }

    @Bean
    public Binding userCodeCreatedDlqBinding(
            @UserCodeCreatedDlqQueue Queue queue,
            @UserCodeCreatedDlqExchange DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("user.code.created.dlq.routing.key");
    }

    @Bean
    public Binding subscriptionCreatedBinding(
            @SubscriptionCreatedQueue Queue queue,
            @SubscriptionCreatedExchange DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("subscription.created.routing.key");
    }

    @Bean
    public Binding subscriptionCreatedDlqBinding(
            @SubscriptionCreatedDlqQueue Queue queue,
            @SubscriptionCreatedDlqExchange DirectExchange directExchange
    ) {
        return BindingBuilder.bind(queue).to(directExchange).with("subscription.created.dlq.routing.key");
    }
}
