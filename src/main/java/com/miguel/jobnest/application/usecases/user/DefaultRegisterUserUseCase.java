package com.miguel.jobnest.application.usecases.user;

import com.miguel.jobnest.application.abstractions.producer.MessageProducer;
import com.miguel.jobnest.application.abstractions.providers.PasswordEncryptionProvider;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.usecases.user.RegisterUserUseCase;
import com.miguel.jobnest.application.usecases.user.inputs.RegisterUserUseCaseInput;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.enums.AuthorizationRole;
import com.miguel.jobnest.domain.events.UserCreatedEvent;
import com.miguel.jobnest.domain.exceptions.DomainException;

public class DefaultRegisterUserUseCase implements RegisterUserUseCase {
    private final UserRepository userRepository;
    private final PasswordEncryptionProvider passwordEncryptionProvider;
    private final MessageProducer messageProducer;

    private static final String USER_CREATED_EXCHANGE = "user.created.exchange";
    private static final String USER_CREATED_ROUTING_KEY = "user.created.routing.key";

    public DefaultRegisterUserUseCase(
            UserRepository userRepository,
            PasswordEncryptionProvider passwordEncryptionProvider,
            MessageProducer messageProducer
    ) {
        this.userRepository = userRepository;
        this.passwordEncryptionProvider = passwordEncryptionProvider;
        this.messageProducer = messageProducer;
    }

    @Override
    public void execute(RegisterUserUseCaseInput input) {
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

        final User savedUser = this.saveUser(newUser);

        final UserCreatedEvent event = UserCreatedEvent.from(savedUser.getId());

        this.messageProducer.publish(USER_CREATED_EXCHANGE, USER_CREATED_ROUTING_KEY, event);
    }

    private boolean verifyUserAlreadyExistsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    private User saveUser(User user) {
        return this.userRepository.save(user);
    }
}
