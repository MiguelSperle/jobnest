package com.miguel.jobnest.infrastructure.consumers.reprocessing;

import com.miguel.jobnest.application.abstractions.producer.MessageProducer;
import com.miguel.jobnest.domain.events.SubscriptionCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscriptionCreatedDlqConsumer {
    private final MessageProducer messageProducer;

    private static final String SUBSCRIPTION_CREATED_DLQ_QUEUE = "subscription.created.dlq.queue";
    private static final String SUBSCRIPTION_CREATED_EXCHANGE = "subscription.created.exchange";
    private static final String SUBSCRIPTION_CREATED_ROUTING_KEY = "subscription.created.routing.key";

    @RabbitListener(queues = SUBSCRIPTION_CREATED_DLQ_QUEUE)
    public void onMessage(SubscriptionCreatedEvent event) {
        this.messageProducer.publish(SUBSCRIPTION_CREATED_EXCHANGE, SUBSCRIPTION_CREATED_ROUTING_KEY, event);
    }
}
