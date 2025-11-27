package com.miguel.jobnest.application.usecases.user;

import com.miguel.jobnest.application.abstractions.providers.PasswordEncryptionProvider;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.usecases.user.UpdateUserPasswordUseCase;
import com.miguel.jobnest.application.usecases.user.inputs.UpdateUserPasswordUseCaseInput;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.exceptions.DomainException;
import com.miguel.jobnest.domain.exceptions.NotFoundException;

public class DefaultUpdateUserPasswordUseCase implements UpdateUserPasswordUseCase {
    private final UserRepository userRepository;
    private final PasswordEncryptionProvider passwordEncryptionProvider;

    public DefaultUpdateUserPasswordUseCase(
            UserRepository userRepository,
            PasswordEncryptionProvider passwordEncryptionProvider
    ) {
        this.userRepository = userRepository;
        this.passwordEncryptionProvider = passwordEncryptionProvider;
    }

    @Override
    public void execute(UpdateUserPasswordUseCaseInput input) {
        final User user = this.getUserById(input.userId());

        if (!this.validatePassword(input.currentPassword(), user.getPassword())) {
            throw DomainException.with("Invalid current password", 422);
        }

        final String encodedPassword = this.passwordEncryptionProvider.encode(input.password());

        final User updatedUser = user.withPassword(encodedPassword);

        this.saveUser(updatedUser);
    }

    private User getUserById(String id) {
        return this.userRepository.findById(id).orElseThrow(() -> NotFoundException.with("User not found"));
    }

    private boolean validatePassword(String password, String encodedPassword) {
        return this.passwordEncryptionProvider.matches(password, encodedPassword);
    }

    private void saveUser(User user) {
        this.userRepository.save(user);
    }
}
