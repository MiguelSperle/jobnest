package com.miguel.jobnest.application.usecases.user;

import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.services.SecurityContextService;
import com.miguel.jobnest.application.abstractions.usecases.user.GetAuthenticatedUserUseCase;
import com.miguel.jobnest.application.usecases.user.outputs.GetAuthenticatedUserUseCaseOutput;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.exceptions.NotFoundException;

public class GetAuthenticatedUserUseCaseImpl implements GetAuthenticatedUserUseCase {
    private final UserRepository userRepository;
    private final SecurityContextService securityContextService;

    public GetAuthenticatedUserUseCaseImpl(
            UserRepository userRepository,
            SecurityContextService securityContextService
    ) {
        this.userRepository = userRepository;
        this.securityContextService = securityContextService;
    }

    @Override
    public GetAuthenticatedUserUseCaseOutput execute() {
        final String authenticatedUserId = this.securityContextService.getAuthenticatedUserId();

        final User user = this.getUserById(authenticatedUserId);

        return GetAuthenticatedUserUseCaseOutput.from(user);
    }

    private User getUserById(String id) {
        return this.userRepository.findById(id).orElseThrow(() -> NotFoundException.with("User not found"));
    }
}
