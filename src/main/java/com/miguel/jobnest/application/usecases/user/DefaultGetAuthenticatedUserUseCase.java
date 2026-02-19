package com.miguel.jobnest.application.usecases.user;

import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.services.SecurityService;
import com.miguel.jobnest.application.abstractions.usecases.user.GetAuthenticatedUserUseCase;
import com.miguel.jobnest.application.usecases.user.outputs.GetAuthenticatedUserUseCaseOutput;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.exceptions.NotFoundException;

public class DefaultGetAuthenticatedUserUseCase implements GetAuthenticatedUserUseCase {
    private final UserRepository userRepository;
    private final SecurityService securityService;

    public DefaultGetAuthenticatedUserUseCase(
            final UserRepository userRepository,
            final SecurityService securityService
    ) {
        this.userRepository = userRepository;
        this.securityService = securityService;
    }

    @Override
    public GetAuthenticatedUserUseCaseOutput execute() {
        final String authenticatedUserId = this.securityService.getPrincipal();

        final User user = this.getUserById(authenticatedUserId);

        return GetAuthenticatedUserUseCaseOutput.from(user);
    }

    private User getUserById(final String id) {
        return this.userRepository.findById(id).orElseThrow(() -> NotFoundException.with("User not found"));
    }
}
