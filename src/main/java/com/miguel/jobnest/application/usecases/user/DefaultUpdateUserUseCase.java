package com.miguel.jobnest.application.usecases.user;

import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.services.SecurityService;
import com.miguel.jobnest.application.abstractions.usecases.user.UpdateUserUseCase;
import com.miguel.jobnest.application.usecases.user.inputs.UpdateUserUseCaseInput;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.exceptions.NotFoundException;

public class DefaultUpdateUserUseCase implements UpdateUserUseCase {
    private final UserRepository userRepository;
    private final SecurityService securityService;

    public DefaultUpdateUserUseCase(
            final UserRepository userRepository,
            final SecurityService securityService
    ) {
        this.userRepository = userRepository;
        this.securityService = securityService;
    }

    @Override
    public void execute(final UpdateUserUseCaseInput input) {
        final String authenticatedUserId = this.securityService.getPrincipal();

        final User user = this.getUserById(authenticatedUserId);

        final User updatedUser = user.withName(input.name()).withEmail(input.email().trim().toLowerCase())
                .withDescription(input.description()).withCity(input.city()).withState(input.state())
                .withCountry(input.country());

        this.saveUser(updatedUser);
    }

    private User getUserById(final String id) {
        return this.userRepository.findById(id).orElseThrow(() -> NotFoundException.with("User not found"));
    }

    private void saveUser(final User user) {
        this.userRepository.save(user);
    }
}
