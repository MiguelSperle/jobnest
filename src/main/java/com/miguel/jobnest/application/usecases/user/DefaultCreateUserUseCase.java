package com.miguel.jobnest.application.usecases.user;

import com.miguel.jobnest.application.abstractions.providers.CodeGenerator;
import com.miguel.jobnest.application.abstractions.providers.PasswordEncryption;
import com.miguel.jobnest.application.abstractions.repositories.UserCodeRepository;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.usecases.user.CreateUserUseCase;
import com.miguel.jobnest.application.abstractions.wrapper.TransactionManager;
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
    private final TransactionManager transactionManager;

    private final static int CODE_LENGTH = 8;
    private final static String ALPHANUMERIC_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public DefaultCreateUserUseCase(
            final UserRepository userRepository,
            final UserCodeRepository userCodeRepository,
            final PasswordEncryption passwordEncryption,
            final CodeGenerator codeGenerator,
            final TransactionManager transactionManager
    ) {
        this.userRepository = userRepository;
        this.userCodeRepository = userCodeRepository;
        this.passwordEncryption = passwordEncryption;
        this.codeGenerator = codeGenerator;
        this.transactionManager = transactionManager;
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

        final String codeGenerated = this.codeGenerator.generateCode(CODE_LENGTH, ALPHANUMERIC_CHARACTERS);

        final UserCode newUserCode = UserCode.newUserCode(newUser.getId(), codeGenerated, UserCodeType.USER_VERIFICATION);

        newUserCode.registerEvent(new UserCodeCreatedEvent(
                newUserCode.getId(),
                newUserCode.getCode(),
                newUserCode.getUserCodeType(),
                newUserCode.getUserId()
        ));

        this.transactionManager.runTransaction(() -> {
            this.saveUser(newUser);
            this.saveUserCode(newUserCode);
        });
    }

    private boolean verifyUserAlreadyExistsByEmail(final String email) {
        return this.userRepository.existsByEmail(email);
    }

    private void saveUser(final User user) {
        this.userRepository.save(user);
    }

    private void saveUserCode(final UserCode userCode) {
        this.userCodeRepository.save(userCode);
    }
}
