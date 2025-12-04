package com.miguel.jobnest.application.usecases.user;

import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.services.SecurityService;
import com.miguel.jobnest.application.abstractions.usecases.user.SoftDeleteUserUseCase;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.enums.UserStatus;
import com.miguel.jobnest.domain.exceptions.NotFoundException;

public class DefaultSoftDeleteUserUseCase implements SoftDeleteUserUseCase {
    private final UserRepository userRepository;
    private final SecurityService securityService;

    public DefaultSoftDeleteUserUseCase(
            UserRepository userRepository,
            SecurityService securityService
    ) {
        this.userRepository = userRepository;
        this.securityService = securityService;
    }

    @Override
    public void execute() {
        final String authenticatedUserId = this.securityService.getPrincipal();

        final User user = this.getUserById(authenticatedUserId);

        final User updatedUser = user.withUserStatus(UserStatus.DELETED);

        this.saveUser(updatedUser);
    }

    private User getUserById(String id) {
        return this.userRepository.findById(id).orElseThrow(() -> NotFoundException.with("User not found"));
    }

    private void saveUser(User user) {
        this.userRepository.save(user);
    }
}
