package com.miguel.jobnest.application.usecases.user;

import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.usecases.user.GetAuthenticatedUserUseCase;
import com.miguel.jobnest.application.usecases.user.inputs.GetAuthenticatedUserUseCaseInput;
import com.miguel.jobnest.application.usecases.user.outputs.GetAuthenticatedUserUseCaseOutput;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.exceptions.NotFoundException;

public class DefaultGetAuthenticatedUserUseCase implements GetAuthenticatedUserUseCase {
    private final UserRepository userRepository;

    public DefaultGetAuthenticatedUserUseCase(
            final UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @Override
    public GetAuthenticatedUserUseCaseOutput execute(final GetAuthenticatedUserUseCaseInput input) {
        final User user = this.getUserById(input.userId());

        return GetAuthenticatedUserUseCaseOutput.from(user);
    }

    private User getUserById(final String id) {
        return this.userRepository.findById(id).orElseThrow(() -> NotFoundException.with("User not found"));
    }
}
