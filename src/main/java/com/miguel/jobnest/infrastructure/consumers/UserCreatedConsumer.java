package com.miguel.jobnest.infrastructure.consumers;

import com.miguel.jobnest.application.abstractions.providers.CodeProvider;
import com.miguel.jobnest.application.abstractions.repositories.UserCodeRepository;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.services.EmailService;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.entities.UserCode;
import com.miguel.jobnest.domain.enums.UserCodeType;
import com.miguel.jobnest.domain.events.UserCreatedEvent;
import com.miguel.jobnest.domain.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserCreatedConsumer {
    private final UserRepository userRepository;
    private final UserCodeRepository userCodeRepository;
    private final CodeProvider codeProvider;
    private final EmailService emailService;

    private static final String USER_CREATED_QUEUE = "user.created.queue";

    private final static int CODE_LENGTH = 8;
    private final static String ALPHANUMERIC_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @RabbitListener(queues = USER_CREATED_QUEUE)
    public void onMessage(UserCreatedEvent event) {
        this.getPreviousUserCodeByUserIdAndCodeType(event.id()).ifPresent(userCode ->
                this.deleteUserCodeById(userCode.getId())
        );

        final String codeGenerated = this.codeProvider.generateCode(CODE_LENGTH, ALPHANUMERIC_CHARACTERS);

        final UserCode newUserCode = UserCode.newUserCode(event.id(), codeGenerated, UserCodeType.USER_VERIFICATION);

        final UserCode savedUserCode = this.saveUserCode(newUserCode);

        final String text = "Hello, your verification code is " + savedUserCode.getCode() + " and it will expire in 15 minutes";
        final String subject = "Verification Code";

        final User user = this.getUserById(savedUserCode.getUserId());

        this.emailService.sendEmail(user.getEmail(), text, subject);
    }

    private UserCode saveUserCode(UserCode userCode) {
        return this.userCodeRepository.save(userCode);
    }

    private User getUserById(String id) {
        return this.userRepository.findById(id).orElseThrow(() -> NotFoundException.with("User not found"));
    }

    private Optional<UserCode> getPreviousUserCodeByUserIdAndCodeType(String userId) {
        return this.userCodeRepository.findByUserIdAndCodeType(userId, UserCodeType.USER_VERIFICATION.name());
    }

    private void deleteUserCodeById(String id) {
        this.userCodeRepository.deleteById(id);
    }
}