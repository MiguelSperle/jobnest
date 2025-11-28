package com.miguel.jobnest.infrastructure.consumers.reprocessing;

import com.miguel.jobnest.application.abstractions.producer.MessageProducer;
import com.miguel.jobnest.domain.events.UserCodeCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCodeCreatedDlqConsumer {
    private final MessageProducer messageProducer;

    private static final String USER_CODE_CREATED_DLQ_QUEUE = "user.code.created.dlq.queue";
    private static final String USER_CODE_CREATED_EXCHANGE = "user.code.created.exchange";
    private static final String USER_CODE_CREATED_ROUTING_KEY = "user.code.created.routing.key";

    @RabbitListener(queues = USER_CODE_CREATED_DLQ_QUEUE)
    public void onMessage(UserCodeCreatedEvent event) {
        this.messageProducer.publish(USER_CODE_CREATED_EXCHANGE, USER_CODE_CREATED_ROUTING_KEY, event);
    }
}
