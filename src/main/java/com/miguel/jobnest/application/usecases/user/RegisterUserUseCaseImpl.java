package com.miguel.jobnest.application.usecases.user;

import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.usecases.user.RegisterUserUseCase;
import com.miguel.jobnest.application.usecases.user.inputs.CreateUserUseCaseInput;

public class RegisterUserUseCaseImpl implements RegisterUserUseCase {
    private final UserRepository userRepository;

    public RegisterUserUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void execute(CreateUserUseCaseInput input) {
        System.out.println(input);
    }
}
