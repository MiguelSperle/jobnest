package com.miguel.jobnest.infrastructure.configurations;

import com.miguel.jobnest.infrastructure.configurations.properties.rabbitmq.RabbitMQProperties;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RabbitMQConfiguration {
    private final RabbitMQProperties rabbitMQProperties;

    public RabbitMQConfiguration(final RabbitMQProperties rabbitMQProperties) {
        this.rabbitMQProperties = rabbitMQProperties;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public Declarables declarables() {
        final List<Declarable> declarables = new ArrayList<>();

        this.rabbitMQProperties.getQueues().forEach((key, queueConfig) -> {
            final Exchange exchange = this.configureExchange(queueConfig.getExchange());

            declarables.add(exchange);

            final Queue queue = this.configureQueue(queueConfig.getQueue(), queueConfig.getDeadLetterQueue());
            declarables.add(queue);

            declarables.add(BindingBuilder.bind(queue).to(exchange).with(queueConfig.getRoutingKey()).noargs());

            if (queueConfig.getDeadLetterQueue() != null) {
                final List<Declarable> deadLetterQueue = this.configureDeadLetterQueue(queueConfig.getDeadLetterQueue());
                declarables.addAll(deadLetterQueue);
            }
        });

        return new Declarables(declarables);
    }

    private Exchange configureExchange(final RabbitMQProperties.ExchangeConfig exchangeConfig) {
        return switch (exchangeConfig.getType()) {
            case "topic" -> new TopicExchange(exchangeConfig.getName(), true, false);
            case "fanout" -> new FanoutExchange(exchangeConfig.getName(), true, false);
            case "direct" -> new DirectExchange(exchangeConfig.getName(), true, false);
            default -> throw new IllegalArgumentException("Invalid exchangeConfig type: " + exchangeConfig.getType());
        };
    }

    private Queue configureQueue(final String queueName, final RabbitMQProperties.DeadLetterQueueConfig deadLetterQueueConfig) {
        final QueueBuilder queueBuilder = QueueBuilder.durable(queueName);

        if (deadLetterQueueConfig != null) {
            queueBuilder.deadLetterExchange(deadLetterQueueConfig.getExchange());
            queueBuilder.deadLetterRoutingKey(deadLetterQueueConfig.getRoutingKey());
        }

        return queueBuilder.build();
    }

    private List<Declarable> configureDeadLetterQueue(final RabbitMQProperties.DeadLetterQueueConfig deadLetterQueueConfig) {
        final List<Declarable> deadLetterQueueDeclarables = new ArrayList<>();

        final DirectExchange deadLetterQueueExchange = new DirectExchange(deadLetterQueueConfig.getExchange(), true, false);

        final Queue deadLetterQueue = QueueBuilder.durable(deadLetterQueueConfig.getName()).build();

        final Binding binding = BindingBuilder.bind(deadLetterQueue).to(deadLetterQueueExchange).with(deadLetterQueueConfig.getRoutingKey());

        deadLetterQueueDeclarables.add(deadLetterQueueExchange);
        deadLetterQueueDeclarables.add(deadLetterQueue);
        deadLetterQueueDeclarables.add(binding);

        return deadLetterQueueDeclarables;
    }
}
