package com.miguel.jobnest.infrastructure.consumers;

import com.miguel.jobnest.application.abstractions.repositories.UserCodeRepository;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.services.EmailService;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.entities.UserCode;
import com.miguel.jobnest.domain.enums.UserCodeType;
import com.miguel.jobnest.domain.events.UserCodeCreatedEvent;
import com.miguel.jobnest.domain.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserCodeCreatedConsumer {
    private final UserCodeRepository userCodeRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    private static final String USER_CODE_CREATED_QUEUE = "user.code.created.queue";

    @RabbitListener(queues = USER_CODE_CREATED_QUEUE)
    public void onMessage(UserCodeCreatedEvent event) {
        final UserCode userCode = this.getUserCodeById(event.id());

        String text;
        String subject;

        if (Objects.equals(userCode.getUserCodeType(), UserCodeType.USER_VERIFICATION)) {
            text = "Hello, your verification code is " + userCode.getCode() + " and it will expire in 15 minutes";
            subject = "Verification Code";
        } else {
            text = "Hello, your password reset code is " + userCode.getCode() + " and it will expire in 15 minutes";
            subject = "Password Reset Code";
        }

        final User user = this.getUserById(userCode.getUserId());

        this.emailService.sendEmail(user.getEmail(), text, subject);
    }

    private UserCode getUserCodeById(String id) {
        return this.userCodeRepository.findById(id).orElseThrow(() -> NotFoundException.with("User code not found"));
    }

    private User getUserById(String id) {
        return this.userRepository.findById(id).orElseThrow(() -> NotFoundException.with("User not found"));
    }
}
