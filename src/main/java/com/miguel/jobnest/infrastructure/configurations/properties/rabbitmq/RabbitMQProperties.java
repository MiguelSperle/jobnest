package com.miguel.jobnest.infrastructure.configurations.properties.rabbitmq;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "broker")
public class RabbitMQProperties {
    private Map<String, QueueProperties> queues;

    public Map<String, QueueProperties> getQueues() {
        return this.queues;
    }

    public void setQueues(final Map<String, QueueProperties> queues) {
        this.queues = queues;
    }

    public static class QueueProperties {
        private Exchange exchange;
        private String routingKey;
        private Queue queue;

        public Exchange getExchange() {
            return this.exchange;
        }

        public void setExchange(final Exchange exchange) {
            this.exchange = exchange;
        }

        public String getRoutingKey() {
            return this.routingKey;
        }

        public void setRoutingKey(final String routingKey) {
            this.routingKey = routingKey;
        }

        public Queue getQueue() {
            return this.queue;
        }

        public void setQueue(final Queue queue) {
            this.queue = queue;
        }
    }

    public static class Exchange {
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

    public static class Queue {
        private String name;
        private DeadLetterQueue deadLetterQueue;

        public String getName() {
            return this.name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public DeadLetterQueue getDeadLetterQueue() {
            return this.deadLetterQueue;
        }

        public void setDeadLetterQueue(final DeadLetterQueue deadLetterQueue) {
            this.deadLetterQueue = deadLetterQueue;
        }
    }

    public static class DeadLetterQueue {
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
