package com.miguel.jobnest.infrastructure.consumers.reprocessing;

import com.miguel.jobnest.application.abstractions.producer.MessageProducer;
import com.miguel.jobnest.domain.events.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCreatedDlqConsumer {
    private final MessageProducer messageProducer;

    private static final String USER_CREATED_DLQ_QUEUE = "user.created.dlq.queue";
    private static final String USER_CREATED_EXCHANGE = "user.created.exchange";
    private static final String USER_CREATED_ROUTING_KEY = "user.created.routing.key";

    @RabbitListener(queues = USER_CREATED_DLQ_QUEUE)
    public void onMessage(UserCreatedEvent userCreatedEvent) {
        this.messageProducer.publish(USER_CREATED_EXCHANGE, USER_CREATED_ROUTING_KEY, userCreatedEvent);
    }
}
