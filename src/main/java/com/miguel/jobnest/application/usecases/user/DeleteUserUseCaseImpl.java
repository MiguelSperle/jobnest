package com.miguel.jobnest.application.usecases.user;

import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.usecases.user.DeleteUserUseCase;
import com.miguel.jobnest.application.usecases.user.inputs.DeleteUserUseCaseInput;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.enums.UserStatus;
import com.miguel.jobnest.domain.exceptions.NotFoundException;

public class DeleteUserUseCaseImpl implements DeleteUserUseCase {
    private final UserRepository userRepository;

    public DeleteUserUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void execute(DeleteUserUseCaseInput input) {
        final User user = this.getUserById(input.userId());

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
