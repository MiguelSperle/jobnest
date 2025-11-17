package com.miguel.jobnest.application.usecases.user.register;

import com.miguel.jobnest.application.abstractions.producer.MessageProducer;
import com.miguel.jobnest.application.abstractions.providers.PasswordEncryptionProvider;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.usecases.user.register.RegisterUserUseCase;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.enums.AuthorizationRole;
import com.miguel.jobnest.domain.events.UserCreatedEvent;
import com.miguel.jobnest.domain.exceptions.DomainException;

public class RegisterUserUseCaseImpl implements RegisterUserUseCase {
    private final UserRepository userRepository;
    private final PasswordEncryptionProvider passwordEncryptionProvider;
    private final MessageProducer messageProducer;

    private static final String USER_CREATED_EXCHANGE = "user.created.exchange";
    private static final String USER_CREATED_ROUTING_KEY = "user.created.routing.key";

    public RegisterUserUseCaseImpl(
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

        final var authorizationRoleConverted = AuthorizationRole.valueOf(input.authorizationRole());

        final User newUser = User.newUser(
                input.name(),
                input.email().toLowerCase(),
                encodedPassword,
                authorizationRoleConverted,
                input.city(),
                input.state(),
                input.country()
        );

        final User savedUser = this.saveUser(newUser);

        final UserCreatedEvent userCreatedEvent = UserCreatedEvent.from(savedUser.getId());

        this.messageProducer.publish(USER_CREATED_EXCHANGE, USER_CREATED_ROUTING_KEY, userCreatedEvent);
    }

    private boolean verifyUserAlreadyExistsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    private User saveUser(User user) {
        return this.userRepository.save(user);
    }
}
