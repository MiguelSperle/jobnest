package com.miguel.jobnest.application.usecases.user;

import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.services.CacheService;
import com.miguel.jobnest.application.abstractions.services.SecurityContextService;
import com.miguel.jobnest.application.abstractions.usecases.user.GetAuthenticatedUserUseCase;
import com.miguel.jobnest.application.usecases.user.outputs.GetAuthenticatedUserUseCaseOutput;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.exceptions.NotFoundException;

import java.time.Duration;

public class GetAuthenticatedUserUseCaseImpl implements GetAuthenticatedUserUseCase {
    private final UserRepository userRepository;
    private final CacheService cacheService;
    private final SecurityContextService securityContextService;

    public GetAuthenticatedUserUseCaseImpl(
            UserRepository userRepository,
            CacheService cacheService,
            SecurityContextService securityContextService
    ) {
        this.userRepository = userRepository;
        this.cacheService = cacheService;
        this.securityContextService = securityContextService;
    }

    @Override
    public GetAuthenticatedUserUseCaseOutput execute() {
        final String authenticatedUserId = this.securityContextService.getAuthenticatedUserId();

        final User user = this.cacheService.get(authenticatedUserId, User.class).orElseGet(() -> {
            final User userFromDatabase = this.getUserById(authenticatedUserId);
            this.cacheService.set(userFromDatabase.getId(), userFromDatabase, Duration.ofMinutes(10));
            return userFromDatabase;
        });

        return GetAuthenticatedUserUseCaseOutput.from(user);
    }

    private User getUserById(String id) {
        return this.userRepository.findById(id).orElseThrow(() -> NotFoundException.with("User not found"));
    }
}
