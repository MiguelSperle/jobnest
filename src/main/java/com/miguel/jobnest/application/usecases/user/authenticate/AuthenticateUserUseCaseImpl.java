package com.miguel.jobnest.application.usecases.user.authenticate;

import com.miguel.jobnest.application.abstractions.providers.PasswordEncryptionProvider;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.services.JwtService;
import com.miguel.jobnest.application.abstractions.usecases.user.authenticate.AuthenticateUserUseCase;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.enums.UserStatus;
import com.miguel.jobnest.domain.exceptions.DomainException;

public class AuthenticateUserUseCaseImpl implements AuthenticateUserUseCase {
    private final UserRepository userRepository;
    private final PasswordEncryptionProvider passwordEncryptionProvider;
    private final JwtService jwtService;

    public AuthenticateUserUseCaseImpl(
            UserRepository userRepository,
            PasswordEncryptionProvider passwordEncryptionProvider,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncryptionProvider = passwordEncryptionProvider;
        this.jwtService = jwtService;
    }

    @Override
    public AuthenticateUserUseCaseOutput execute(AuthenticateUserUseCaseInput input) {
        final User user = this.getUserByEmail(input.email());

        if (!this.validatePassword(input.password(), user.getPassword())) {
            throw DomainException.with("Wrong credentials", 401);
        }

        if (user.getUserStatus() == UserStatus.UNVERIFIED) {
            throw DomainException.with("User not verified", 403);
        }

        if (user.getUserStatus() == UserStatus.DELETED) {
            throw DomainException.with("User has been deleted", 403);
        }

        final String jwtGenerated = this.jwtService.generateJwt(user.getId(), user.getAuthorizationRole().name());

        return AuthenticateUserUseCaseOutput.from(jwtGenerated);
    }

    private User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> DomainException.with("Wrong credentials", 401));
    }

    private boolean validatePassword(String password, String encodedPassword) {
        return this.passwordEncryptionProvider.matches(password, encodedPassword);
    }
}
