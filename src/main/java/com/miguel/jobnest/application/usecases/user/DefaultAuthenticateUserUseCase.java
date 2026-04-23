package com.miguel.jobnest.application.usecases.user;

import com.miguel.jobnest.application.abstractions.providers.PasswordEncryption;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.services.JwtTokenService;
import com.miguel.jobnest.application.abstractions.usecases.user.AuthenticateUserUseCase;
import com.miguel.jobnest.application.usecases.user.inputs.AuthenticateUserUseCaseInput;
import com.miguel.jobnest.application.usecases.user.outputs.AuthenticateUserUseCaseOutput;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.enums.UserStatus;
import com.miguel.jobnest.domain.exceptions.DomainException;

import java.util.List;

public class DefaultAuthenticateUserUseCase implements AuthenticateUserUseCase {
    private final UserRepository userRepository;
    private final PasswordEncryption passwordEncryption;
    private final JwtTokenService jwtTokenService;

    public DefaultAuthenticateUserUseCase(
            final UserRepository userRepository,
            final PasswordEncryption passwordEncryption,
            final JwtTokenService jwtTokenService
    ) {
        this.userRepository = userRepository;
        this.passwordEncryption = passwordEncryption;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public AuthenticateUserUseCaseOutput execute(final AuthenticateUserUseCaseInput input) {
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

        final String jwtGenerated = this.jwtTokenService.generateAccessToken(user.getId(), user.getAuthorizationRole().name(), List.of());

        return AuthenticateUserUseCaseOutput.from(jwtGenerated);
    }

    private User getUserByEmail(final String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> DomainException.with("Wrong credentials", 401));
    }

    private boolean validatePassword(final String password, final String encodedPassword) {
        return this.passwordEncryption.matches(password, encodedPassword);
    }
}
