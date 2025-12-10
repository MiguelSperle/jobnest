package com.miguel.jobnest.application.usecases.user;

import com.miguel.jobnest.application.abstractions.producer.MessageProducer;
import com.miguel.jobnest.application.abstractions.providers.CodeProvider;
import com.miguel.jobnest.application.abstractions.providers.PasswordEncryptionProvider;
import com.miguel.jobnest.application.abstractions.repositories.UserCodeRepository;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.usecases.user.CreateUserUseCase;
import com.miguel.jobnest.application.abstractions.wrapper.TransactionExecutor;
import com.miguel.jobnest.application.usecases.user.inputs.CreateUserUseCaseInput;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.entities.UserCode;
import com.miguel.jobnest.domain.enums.AuthorizationRole;
import com.miguel.jobnest.domain.enums.UserCodeType;
import com.miguel.jobnest.domain.events.UserCodeCreatedEvent;
import com.miguel.jobnest.domain.exceptions.DomainException;

public class DefaultCreateUserUseCase implements CreateUserUseCase {
    private final UserRepository userRepository;
    private final UserCodeRepository userCodeRepository;
    private final PasswordEncryptionProvider passwordEncryptionProvider;
    private final CodeProvider codeProvider;
    private final TransactionExecutor transactionExecutor;
    private final MessageProducer messageProducer;

    private final static int CODE_LENGTH = 8;
    private final static String ALPHANUMERIC_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final String USER_CODE_CREATED_EXCHANGE = "user.code.created.exchange";
    private static final String USER_CODE_CREATED_ROUTING_KEY = "user.code.created.routing.key";

    public DefaultCreateUserUseCase(
            UserRepository userRepository,
            UserCodeRepository userCodeRepository,
            PasswordEncryptionProvider passwordEncryptionProvider,
            CodeProvider codeProvider,
            TransactionExecutor transactionExecutor,
            MessageProducer messageProducer
    ) {
        this.userRepository = userRepository;
        this.userCodeRepository = userCodeRepository;
        this.passwordEncryptionProvider = passwordEncryptionProvider;
        this.codeProvider = codeProvider;
        this.transactionExecutor = transactionExecutor;
        this.messageProducer = messageProducer;
    }

    @Override
    public void execute(CreateUserUseCaseInput input) {
        if (this.verifyUserAlreadyExistsByEmail(input.email())) {
            throw DomainException.with("This email is already being used", 409);
        }

        final String encodedPassword = this.passwordEncryptionProvider.encode(input.password());

        final AuthorizationRole convertedAuthorizationRole = AuthorizationRole.valueOf(input.authorizationRole());

        final User newUser = User.newUser(
                input.name(),
                input.email().toLowerCase(),
                encodedPassword,
                convertedAuthorizationRole,
                input.city(),
                input.state(),
                input.country()
        );

        this.transactionExecutor.runTransaction(() -> {
            final User savedUser = this.saveUser(newUser);

            final String codeGenerated = this.codeProvider.generateCode(CODE_LENGTH, ALPHANUMERIC_CHARACTERS);

            final UserCode newUserCode = UserCode.newUserCode(savedUser.getId(), codeGenerated, UserCodeType.USER_VERIFICATION);

            final UserCode savedUserCode = this.saveUserCode(newUserCode);

            final UserCodeCreatedEvent event = UserCodeCreatedEvent.from(
                    savedUserCode.getCode(),
                    savedUserCode.getUserCodeType(),
                    savedUserCode.getUserId()
            );

            this.transactionExecutor.registerAfterCommit(() -> this.messageProducer.publish(
                    USER_CODE_CREATED_EXCHANGE, USER_CODE_CREATED_ROUTING_KEY, event
            ));
        });
    }

    private boolean verifyUserAlreadyExistsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    private User saveUser(User user) {
        return this.userRepository.save(user);
    }

    private UserCode saveUserCode(UserCode userCode) {
        return this.userCodeRepository.save(userCode);
    }
}
