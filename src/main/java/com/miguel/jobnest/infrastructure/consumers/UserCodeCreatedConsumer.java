package com.miguel.jobnest.infrastructure.consumers;

import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.services.EmailService;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.enums.UserCodeType;
import com.miguel.jobnest.domain.events.UserCodeCreatedEvent;
import com.miguel.jobnest.domain.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserCodeCreatedConsumer {
    private final UserRepository userRepository;
    private final EmailService emailService;

    private static final String USER_CODE_CREATED_QUEUE = "user.code.created.queue";

    @RabbitListener(queues = USER_CODE_CREATED_QUEUE)
    public void onMessage(UserCodeCreatedEvent event) {
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

        log.info("Message {} with payload {} has been processed successfully", event.getClass().getSimpleName(), event);
    }

    private User getUserById(String id) {
        return this.userRepository.findById(id).orElseThrow(() -> NotFoundException.with("User not found"));
    }
}
