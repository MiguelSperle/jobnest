package com.miguel.jobnest.infrastructure.listeners;

import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.domain.enums.UserCodeType;
import com.miguel.jobnest.domain.events.UserCodeCreatedEvent;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.exceptions.NotFoundException;
import com.miguel.jobnest.infrastructure.abstractions.services.EmailService;
import com.miguel.jobnest.infrastructure.configurations.json.Json;
import com.miguel.jobnest.infrastructure.persistence.jpa.entities.JpaProcessedEventEntity;
import com.miguel.jobnest.infrastructure.persistence.jpa.repositories.JpaProcessedEventRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCodeCreatedListener {
    private final UserRepository userRepository;
    private final JpaProcessedEventRepository processedEventRepository;
    private final EmailService emailService;

    private static final String USER_CODE_CREATED_QUEUE = "user.code.created.queue";

    private static final Logger log = LoggerFactory.getLogger(UserCodeCreatedListener.class);

    @RabbitListener(queues = USER_CODE_CREATED_QUEUE)
    public void onMessage(@Payload final Message message) {
        final UserCodeCreatedEvent event = Json.readValue(message.getBody(), UserCodeCreatedEvent.class);

        final String eventId = event.eventId();
        final String listenerName = UserCodeCreatedListener.class.getSimpleName();

        if (this.processedEventRepository.existsByEventIdAndListener(eventId, listenerName)) {
            log.info("Event with id: {} has already been processed by the listener: {}", eventId, listenerName);
            return;
        }

        String text;
        String subject;

        if (event.userCodeType() == UserCodeType.USER_VERIFICATION) {
            text = "Hello, your verification code is " + event.code() + " and it will expire in 15 minutes";
            subject = "Verification Code";
        } else {
            text = "Hello, your password reset code is " + event.code() + " and it will expire in 15 minutes";
            subject = "Password Reset Code";
        }

        final User user = this.getUserById(event.userId());

        this.emailService.sendEmail(user.getEmail(), text, subject);

        this.processedEventRepository.save(JpaProcessedEventEntity.newProcessedEventEntity(eventId, listenerName));

        log.info("Event with id: {} has been successfully processed by the listener: {}", eventId, listenerName);
    }

    private User getUserById(final String id) {
        return this.userRepository.findById(id).orElseThrow(() -> NotFoundException.with("User not found"));
    }
}
