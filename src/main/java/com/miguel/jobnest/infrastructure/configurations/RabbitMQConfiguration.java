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

        this.rabbitMQProperties.getQueues().forEach((key, queueProperties) -> {
            final Exchange exchange = this.configureExchange(queueProperties.getExchangeProperties());
            declarables.add(exchange);

            final Queue queue = this.configureQueue(queueProperties.getQueue(), queueProperties.getDeadLetterQueueProperties());
            declarables.add(queue);

            declarables.add(BindingBuilder.bind(queue).to(exchange).with(queueProperties.getRoutingKey()).noargs());

            if (queueProperties.getDeadLetterQueueProperties() != null) {
                final List<Declarable> deadLetterQueue = this.configureDeadLetterQueue(queueProperties.getDeadLetterQueueProperties());
                declarables.addAll(deadLetterQueue);
            }
        });

        return new Declarables(declarables);
    }

    private Exchange configureExchange(final RabbitMQProperties.ExchangeProperties exchangeProperties) {
        return switch (exchangeProperties.getType()) {
            case "topic" -> new TopicExchange(exchangeProperties.getName(), true, false);
            case "fanout" -> new FanoutExchange(exchangeProperties.getName(), true, false);
            case "direct" -> new DirectExchange(exchangeProperties.getName(), true, false);
            default -> throw new IllegalArgumentException("Invalid exchange type: " + exchangeProperties.getType());
        };
    }

    private Queue configureQueue(final String queue, final RabbitMQProperties.DeadLetterQueueProperties deadLetterQueueProperties) {
        final QueueBuilder queueBuilder = QueueBuilder.durable(queue);

        if (deadLetterQueueProperties != null) {
            queueBuilder.deadLetterExchange(deadLetterQueueProperties.getExchange());
            queueBuilder.deadLetterRoutingKey(deadLetterQueueProperties.getRoutingKey());
        }

        return queueBuilder.build();
    }

    private List<Declarable> configureDeadLetterQueue(final RabbitMQProperties.DeadLetterQueueProperties deadLetterQueueProperties) {
        final List<Declarable> deadLetterQueueDeclarables = new ArrayList<>();

        final DirectExchange directExchange = new DirectExchange(deadLetterQueueProperties.getExchange(), true, false);

        final Queue queue = QueueBuilder.durable(deadLetterQueueProperties.getQueue()).build();

        final Binding binding = BindingBuilder.bind(queue).to(directExchange).with(deadLetterQueueProperties.getRoutingKey());

        deadLetterQueueDeclarables.add(directExchange);
        deadLetterQueueDeclarables.add(queue);
        deadLetterQueueDeclarables.add(binding);

        return deadLetterQueueDeclarables;
    }
}
