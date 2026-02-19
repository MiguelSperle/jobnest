package com.miguel.jobnest.application.usecases.user;

import com.miguel.jobnest.application.abstractions.providers.CodeGenerator;
import com.miguel.jobnest.application.abstractions.providers.PasswordEncryption;
import com.miguel.jobnest.application.abstractions.repositories.EventOutboxRepository;
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
    private final PasswordEncryption passwordEncryption;
    private final CodeGenerator codeGenerator;
    private final TransactionExecutor transactionExecutor;
    private final EventOutboxRepository eventOutboxRepository;

    private final static int CODE_LENGTH = 8;
    private final static String ALPHANUMERIC_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final String USER_CODE_CREATED_EXCHANGE = "user.code.created.exchange";
    private static final String USER_CODE_CREATED_ROUTING_KEY = "user.code.created.routing.key";

    public DefaultCreateUserUseCase(
            final UserRepository userRepository,
            final UserCodeRepository userCodeRepository,
            final PasswordEncryption passwordEncryption,
            final CodeGenerator codeGenerator,
            final TransactionExecutor transactionExecutor,
            final EventOutboxRepository eventOutboxRepository
    ) {
        this.userRepository = userRepository;
        this.userCodeRepository = userCodeRepository;
        this.passwordEncryption = passwordEncryption;
        this.codeGenerator = codeGenerator;
        this.transactionExecutor = transactionExecutor;
        this.eventOutboxRepository = eventOutboxRepository;
    }

    @Override
    public void execute(final CreateUserUseCaseInput input) {
        if (this.verifyUserAlreadyExistsByEmail(input.email())) {
            throw DomainException.with("This email is already being used", 409);
        }

        final String encodedPassword = this.passwordEncryption.encode(input.password());

        final AuthorizationRole convertedAuthorizationRole = AuthorizationRole.valueOf(input.authorizationRole());

        final User newUser = User.newUser(
                input.name(),
                input.email().trim().toLowerCase(),
                encodedPassword,
                convertedAuthorizationRole,
                input.city(),
                input.state(),
                input.country()
        );

        this.transactionExecutor.runTransaction(() -> {
            final User savedUser = this.saveUser(newUser);

            final String codeGenerated = this.codeGenerator.generateCode(CODE_LENGTH, ALPHANUMERIC_CHARACTERS);

            final UserCode newUserCode = UserCode.newUserCode(savedUser.getId(), codeGenerated, UserCodeType.USER_VERIFICATION);

            final UserCode savedUserCode = this.saveUserCode(newUserCode);

            this.eventOutboxRepository.save(
                    USER_CODE_CREATED_EXCHANGE, USER_CODE_CREATED_ROUTING_KEY, new UserCodeCreatedEvent(
                            savedUserCode.getId(),
                            savedUserCode.getCode(),
                            savedUserCode.getUserCodeType(),
                            savedUserCode.getUserId()
                    )
            );
        });
    }

    private boolean verifyUserAlreadyExistsByEmail(final String email) {
        return this.userRepository.existsByEmail(email);
    }

    private User saveUser(final User user) {
        return this.userRepository.save(user);
    }

    private UserCode saveUserCode(final UserCode userCode) {
        return this.userCodeRepository.save(userCode);
    }
}
