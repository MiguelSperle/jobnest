package com.miguel.jobnest.application.usecases.user;

import com.miguel.jobnest.application.abstractions.providers.PasswordEncryption;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.services.SecurityService;
import com.miguel.jobnest.application.abstractions.usecases.user.UpdateUserPasswordUseCase;
import com.miguel.jobnest.application.usecases.user.inputs.UpdateUserPasswordUseCaseInput;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.exceptions.DomainException;
import com.miguel.jobnest.domain.exceptions.NotFoundException;

public class DefaultUpdateUserPasswordUseCase implements UpdateUserPasswordUseCase {
    private final UserRepository userRepository;
    private final PasswordEncryption passwordEncryption;
    private final SecurityService securityService;

    public DefaultUpdateUserPasswordUseCase(
            UserRepository userRepository,
            PasswordEncryption passwordEncryption,
            SecurityService securityService
    ) {
        this.userRepository = userRepository;
        this.passwordEncryption = passwordEncryption;
        this.securityService = securityService;
    }

    @Override
    public void execute(UpdateUserPasswordUseCaseInput input) {
        final String authenticatedUserId = this.securityService.getPrincipal();

        final User user = this.getUserById(authenticatedUserId);

        if (!this.validatePassword(input.currentPassword(), user.getPassword())) {
            throw DomainException.with("Invalid current password", 422);
        }

        final String encodedPassword = this.passwordEncryption.encode(input.password());

        final User updatedUser = user.withPassword(encodedPassword);

        this.saveUser(updatedUser);
    }

    private User getUserById(String id) {
        return this.userRepository.findById(id).orElseThrow(() -> NotFoundException.with("User not found"));
    }

    private boolean validatePassword(String password, String encodedPassword) {
        return this.passwordEncryption.matches(password, encodedPassword);
    }

    private void saveUser(User user) {
        this.userRepository.save(user);
    }
}
