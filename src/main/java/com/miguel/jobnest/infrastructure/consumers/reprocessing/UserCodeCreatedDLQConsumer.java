package com.miguel.jobnest.infrastructure.consumers.reprocessing;

import com.miguel.jobnest.infrastructure.abstractions.producer.MessageProducer;
import com.miguel.jobnest.domain.events.UserCodeCreatedEvent;
import com.miguel.jobnest.infrastructure.configurations.json.Json;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCodeCreatedDLQConsumer {
//    private final MessageProducer messageProducer;
//
//    private static final String USER_CODE_CREATED_DLQ_QUEUE = "user.code.created.dlq.queue";
//    private static final String USER_CODE_CREATED_EXCHANGE = "user.code.created.exchange";
//    private static final String USER_CODE_CREATED_ROUTING_KEY = "user.code.created.routing.key";
//
//    private static final Logger log = LoggerFactory.getLogger(UserCodeCreatedDLQConsumer.class);
//
//    @RabbitListener(queues = USER_CODE_CREATED_DLQ_QUEUE)
//    public void onMessage(Message message) {
//        final UserCodeCreatedEvent event = Json.readValue(message.getBody(), UserCodeCreatedEvent.class);
//
//        this.messageProducer.publish(USER_CODE_CREATED_EXCHANGE, USER_CODE_CREATED_ROUTING_KEY, event);
//        log.info("Message with id: {} was sent back to the original queue", event.eventId());
//    }
}
