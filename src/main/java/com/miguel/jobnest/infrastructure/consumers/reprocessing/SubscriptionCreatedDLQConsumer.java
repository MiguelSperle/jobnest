package com.miguel.jobnest.infrastructure.consumers.reprocessing;

import com.miguel.jobnest.infrastructure.abstractions.producer.MessageProducer;
import com.miguel.jobnest.domain.events.SubscriptionCreatedEvent;
import com.miguel.jobnest.infrastructure.configurations.json.Json;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscriptionCreatedDLQConsumer {
//    private final MessageProducer messageProducer;
//
//    private static final String SUBSCRIPTION_CREATED_DLQ_QUEUE = "subscription.created.dlq.queue";
//    private static final String SUBSCRIPTION_CREATED_EXCHANGE = "subscription.created.exchange";
//    private static final String SUBSCRIPTION_CREATED_ROUTING_KEY = "subscription.created.routing.key";
//
//    private static final Logger log = LoggerFactory.getLogger(SubscriptionCreatedDLQConsumer.class);
//
//    @RabbitListener(queues = SUBSCRIPTION_CREATED_DLQ_QUEUE)
//    public void onMessage(Message message) {
//        final SubscriptionCreatedEvent event = Json.readValue(message.getBody(), SubscriptionCreatedEvent.class);
//
//        this.messageProducer.publish(SUBSCRIPTION_CREATED_EXCHANGE, SUBSCRIPTION_CREATED_ROUTING_KEY, event);
//        log.info("Message with id: {} was sent back to the original queue", event.eventId());
//    }
}
