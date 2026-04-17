package com.miguel.jobnest.application.usecases.user;

import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.usecases.user.SoftDeleteUserUseCase;
import com.miguel.jobnest.application.usecases.user.inputs.SoftDeleteUserUseCaseInput;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.enums.UserStatus;
import com.miguel.jobnest.domain.exceptions.NotFoundException;

public class DefaultSoftDeleteUserUseCase implements SoftDeleteUserUseCase {
    private final UserRepository userRepository;

    public DefaultSoftDeleteUserUseCase(
            final UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @Override
    public void execute(final SoftDeleteUserUseCaseInput input) {
        final User user = this.getUserById(input.userId());

        final User updatedUser = user.withUserStatus(UserStatus.DELETED);

        this.saveUser(updatedUser);
    }

    private User getUserById(final String id) {
        return this.userRepository.findById(id).orElseThrow(() -> NotFoundException.with("User not found"));
    }

    private void saveUser(final User user) {
        this.userRepository.save(user);
    }
}
