package com.miguel.jobnest.infrastructure.consumers;

import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.services.EmailService;
import com.miguel.jobnest.application.abstractions.services.RedisService;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.enums.UserCodeType;
import com.miguel.jobnest.domain.events.UserCodeCreatedEvent;
import com.miguel.jobnest.domain.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserCodeCreatedConsumer {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final RedisService redisService;

    private static final String USER_CODE_CREATED_QUEUE = "user.code.created.queue";

    private static final String EVENT_PREFIX = "event:UserCodeCreatedEvent:";
    private static final String CONSUMER_SUFFIX = ":consumer:UserCodeCreatedConsumer";

    private static final long ttl = 1;
    private static final TimeUnit timeUnit = TimeUnit.HOURS;

    @RabbitListener(queues = USER_CODE_CREATED_QUEUE)
    public void onMessage(UserCodeCreatedEvent event) {
        final String eventId = event.eventId();
        final String redisKey = EVENT_PREFIX.concat(eventId).concat(CONSUMER_SUFFIX);
        final String eventName = event.getClass().getSimpleName();

        if (this.redisService.existsByKey(redisKey)) {
            log.info("Event has already been processed | eventName {}, eventId {}, payload {}", eventName, eventId, event);
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

        this.redisService.set(redisKey, "processed", ttl, timeUnit);

        log.info("Event has been processed successfully | eventName {}, eventId {}, payload {}", eventName, eventId, event);
    }

    private User getUserById(String id) {
        return this.userRepository.findById(id).orElseThrow(() -> NotFoundException.with("User not found"));
    }
}
