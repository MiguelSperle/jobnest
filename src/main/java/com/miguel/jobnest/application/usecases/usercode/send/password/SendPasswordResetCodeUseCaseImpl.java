package com.miguel.jobnest.application.usecases.usercode.send.password;

import com.miguel.jobnest.application.abstractions.producer.MessageProducer;
import com.miguel.jobnest.application.abstractions.providers.CodeProvider;
import com.miguel.jobnest.application.abstractions.repositories.UserCodeRepository;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.usecases.usercode.send.password.SendPasswordResetCodeUseCase;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.entities.UserCode;
import com.miguel.jobnest.domain.enums.UserCodeType;
import com.miguel.jobnest.domain.events.UserCodeCreatedEvent;
import com.miguel.jobnest.domain.exceptions.NotFoundException;

import java.util.Optional;

public class SendPasswordResetCodeUseCaseImpl implements SendPasswordResetCodeUseCase {
    private final UserRepository userRepository;
    private final UserCodeRepository userCodeRepository;
    private final CodeProvider codeProvider;
    private final MessageProducer messageProducer;

    private final static int CODE_LENGTH = 6;
    private final static String ALPHANUMERIC_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final String USER_CODE_CREATED_EXCHANGE = "user.code.created.exchange";
    private static final String USER_CODE_CREATED_ROUTING_KEY = "user.code.created.routing.key";

    public SendPasswordResetCodeUseCaseImpl(
            UserRepository userRepository,
            UserCodeRepository userCodeRepository,
            CodeProvider codeProvider,
            MessageProducer messageProducer
    ) {
        this.userRepository = userRepository;
        this.userCodeRepository = userCodeRepository;
        this.codeProvider = codeProvider;
        this.messageProducer = messageProducer;
    }

    @Override
    public void execute(SendPasswordResetCodeUseCaseInput input) {
        final User user = this.getUserByEmail(input.email());

        this.getPreviousUserCodeByUserIdAndCodeType(user.getId()).ifPresent(userCode ->
                this.deleteUserCodeById(userCode.getId())
        );

        final String codeGenerated = this.codeProvider.generateCode(CODE_LENGTH, ALPHANUMERIC_CHARACTERS);

        final UserCode newUserCode = UserCode.newUserCode(user.getId(), codeGenerated, UserCodeType.PASSWORD_RESET);

        final UserCode savedUserCode = this.saveUserCode(newUserCode);

        final UserCodeCreatedEvent userCodeCreatedEvent = UserCodeCreatedEvent.from(savedUserCode.getId());

        this.messageProducer.publish(USER_CODE_CREATED_EXCHANGE, USER_CODE_CREATED_ROUTING_KEY, userCodeCreatedEvent);
    }

    private User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> NotFoundException.with("User not found"));
    }

    private Optional<UserCode> getPreviousUserCodeByUserIdAndCodeType(String id) {
        return this.userCodeRepository.findByUserIdAndCodeType(id, UserCodeType.USER_VERIFICATION.name());
    }

    private void deleteUserCodeById(String id) {
        this.userCodeRepository.deleteById(id);
    }

    private UserCode saveUserCode(UserCode userCode) {
        return this.userCodeRepository.save(userCode);
    }
}
