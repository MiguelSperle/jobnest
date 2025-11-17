package com.miguel.jobnest.application.abstractions.usecases.user;

import com.miguel.jobnest.application.abstractions.usecases.UseCase;
import com.miguel.jobnest.application.usecases.user.inputs.AuthenticateUserUseCaseInput;
import com.miguel.jobnest.application.usecases.user.outputs.AuthenticateUserUseCaseOutput;

public interface AuthenticateUserUseCase extends UseCase<AuthenticateUserUseCaseInput, AuthenticateUserUseCaseOutput> {
}
