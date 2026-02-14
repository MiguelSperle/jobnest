package com.miguel.jobnest.infrastructure.consumers;

import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.domain.events.UserCodeCreatedEvent;
import com.miguel.jobnest.infrastructure.abstractions.services.EmailService;
import com.miguel.jobnest.infrastructure.abstractions.services.RedisService;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.exceptions.NotFoundException;
import com.miguel.jobnest.infrastructure.configurations.json.Json;
import com.miguel.jobnest.infrastructure.persistence.jpa.repositories.JpaProcessedEventRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class UserCodeCreatedConsumer {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final JpaProcessedEventRepository processedMessageRepository;

    private static final String USER_CODE_CREATED_QUEUE = "user.code.created.queue";

    private static final Logger log = LoggerFactory.getLogger(UserCodeCreatedConsumer.class);

    @RabbitListener(queues = USER_CODE_CREATED_QUEUE)
    public void onMessage(Message message) {
        System.out.println("UserCodeCreatedConsumer");
        System.out.println(Json.readValue(message.getBody(), UserCodeCreatedEvent.class));
//
//        final String eventId = event.eventId();
//        final String eventName = event.getClass().getSimpleName();
//        final String consumerName = UserCodeCreatedConsumer.class.getSimpleName();
//
//        final String redisKey = "event:".concat(eventName).concat(":").concat(eventId).concat(":consumer:").concat(consumerName);
//
//        if (this.redisService.existsKey(redisKey)) {
//            log.info("Redis key found, retrying to save processed message with id: {} in a consistent database", eventId);
//            this.saveProcessedMessage(eventId, consumerName);
//            return;
//        }
//
//        if (this.processedMessageRepository.existsByMessageIdAndConsumedBy(eventId, consumerName)) {
//            log.info("Message with id: {} has already been processed", eventId);
//            return;
//        }
//
//        String text;
//        String subject;
//
//        if (event.userCodeType() == UserCodeType.USER_VERIFICATION) {
//            text = "Hello, your verification code is " + event.code() + " and it will expire in 15 minutes";
//            subject = "Verification Code";
//        } else {
//            text = "Hello, your password reset code is " + event.code() + " and it will expire in 15 minutes";
//            subject = "Password Reset Code";
//        }
//
//        final User user = this.getUserById(event.userId());
//
//        this.emailService.sendEmail(user.getEmail(), text, subject);
//
//        try {
//            this.saveProcessedMessage(eventId, consumerName);
//            log.info("Message with id: {} has been processed successfully", eventId);
//        } catch (Exception ex) {
//            log.error("Failed to persist processed message with id: {}, falling back to Redis to ensure idempotency", eventId, ex);
//            this.redisService.set(redisKey, "", ttl, timeUnit);
//            throw ex;
//        }
    }

    private User getUserById(String id) {
        return this.userRepository.findById(id).orElseThrow(() -> NotFoundException.with("User not found"));
    }

//    private void saveProcessedMessage(String eventId, String consumerName) {
//        this.processedMessageRepository.save(JpaProcessedMessageEntity.from(eventId, consumerName));
//    }
}
