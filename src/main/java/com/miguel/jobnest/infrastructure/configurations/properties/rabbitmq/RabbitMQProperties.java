package com.miguel.jobnest.infrastructure.configurations.properties.rabbitmq;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "broker")
public class RabbitMQProperties {
    private Map<String, QueueConfig> queues;

    public Map<String, QueueConfig> getQueues() {
        return this.queues;
    }

    public void setQueues(Map<String, QueueConfig> queues) {
        this.queues = queues;
    }

    public static class QueueConfig {
        private ExchangeConfig exchange;
        private String routingKey;
        private String queue;
        private DeadLetterQueueConfig deadLetterQueue;

        public ExchangeConfig getExchange() {
            return this.exchange;
        }

        public void setExchange(final ExchangeConfig exchange) {
            this.exchange = exchange;
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

        public DeadLetterQueueConfig getDeadLetterQueue() {
            return this.deadLetterQueue;
        }

        public void setDeadLetterQueue(final DeadLetterQueueConfig deadLetterQueue) {
            this.deadLetterQueue = deadLetterQueue;
        }
    }

    public static class ExchangeConfig {
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

    public static class DeadLetterQueueConfig {
        private String name;
        private String exchange;
        private String routingKey;

        public String getName() {
            return this.name;
        }

        public void setName(final String name) {
            this.name = name;
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
