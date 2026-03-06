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

        this.rabbitMQProperties.getMessageConfigurations().forEach((key, messageConfiguration) -> {
            final Exchange exchange = this.configureExchange(messageConfiguration.getExchange());

            declarables.add(exchange);

            final Queue queue = this.configureQueue(messageConfiguration.getQueue());
            declarables.add(queue);

            declarables.add(BindingBuilder.bind(queue).to(exchange).with(messageConfiguration.getRoutingKey()).noargs());

            if (messageConfiguration.getQueue().getDeadLetterQueue() != null) {
                final List<Declarable> deadLetterQueue = this.configureDeadLetterQueue(messageConfiguration.getQueue());
                declarables.addAll(deadLetterQueue);
            }
        });

        return new Declarables(declarables);
    }

    private Exchange configureExchange(final RabbitMQProperties.Exchange exchange) {
        return switch (exchange.getType()) {
            case "topic" -> new TopicExchange(exchange.getName(), true, false);
            case "fanout" -> new FanoutExchange(exchange.getName(), true, false);
            case "direct" -> new DirectExchange(exchange.getName(), true, false);
            default -> throw new IllegalArgumentException("Unknown exchange type: " + exchange.getType());
        };
    }

    private Queue configureQueue(final RabbitMQProperties.Queue queue) {
        final QueueBuilder queueBuilder = QueueBuilder.durable(queue.getName());

        if (queue.getDeadLetterQueue() != null) {
            queueBuilder.deadLetterExchange(queue.getDeadLetterQueue().getExchange());
            queueBuilder.deadLetterRoutingKey(queue.getDeadLetterQueue().getRoutingKey());
        }

        return queueBuilder.build();
    }

    private List<Declarable> configureDeadLetterQueue(final RabbitMQProperties.Queue queue) {
        final List<Declarable> deadLetterQueueDeclarables = new ArrayList<>();

        final DirectExchange deadLetterQueueExchange = new DirectExchange(queue.getDeadLetterQueue().getExchange(), true, false);

        final Queue deadLetterQueue = QueueBuilder.durable(queue.getDeadLetterQueue().getName()).build();

        final Binding binding = BindingBuilder.bind(deadLetterQueue).to(deadLetterQueueExchange).with(queue.getDeadLetterQueue().getRoutingKey());

        deadLetterQueueDeclarables.add(deadLetterQueueExchange);
        deadLetterQueueDeclarables.add(deadLetterQueue);
        deadLetterQueueDeclarables.add(binding);

        return deadLetterQueueDeclarables;
    }
}
