package com.miguel.jobnest.infrastructure.configurations.properties.rabbitmq;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "amqp")
public class RabbitMQProperties {
    private Map<String, QueueProperties> queues = new HashMap<>();

    public Map<String, QueueProperties> getQueues() {
        return this.queues;
    }

    public void setQueues(final Map<String, QueueProperties> queues) {
        this.queues = queues;
    }

    public static class QueueProperties {
        private ExchangeProperties exchangeProperties;
        private String routingKey;
        private String queue;
        private DeadLetterQueueProperties deadLetterQueueProperties;

        public ExchangeProperties getExchangeProperties() {
            return this.exchangeProperties;
        }

        public void setExchangeProperties(final ExchangeProperties exchangeProperties) {
            this.exchangeProperties = exchangeProperties;
        }

        public String getRoutingKey() {
            return this.routingKey;
        }

        public void setRoutingKey(final String routingKey) {
            this.routingKey = routingKey;
        }

        public String getQueue() {
            return this.queue;
        }

        public void setQueue(final String queue) {
            this.queue = queue;
        }

        public DeadLetterQueueProperties getDeadLetterQueueProperties() {
            return this.deadLetterQueueProperties;
        }

        public void setDeadLetterQueueProperties(final DeadLetterQueueProperties deadLetterQueueProperties) {
            this.deadLetterQueueProperties = deadLetterQueueProperties;
        }
    }

    public static class ExchangeProperties {
        private String name;
        private String type;

        public String getName() {
            return this.name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getType() {
            return this.type;
        }

        public void setType(final String type) {
            this.type = type;
        }
    }

    public static class DeadLetterQueueProperties {
        private String queue;
        private String exchange;
        private String routingKey;

        public String getQueue() {
            return this.queue;
        }

        public void setQueue(final String queue) {
            this.queue = queue;
        }

        public String getExchange() {
            return this.exchange;
        }

        public void setExchange(final String exchange) {
            this.exchange = exchange;
        }

        public String getRoutingKey() {
            return this.routingKey;
        }

        public void setRoutingKey(final String routingKey) {
            this.routingKey = routingKey;
        }
    }
}
