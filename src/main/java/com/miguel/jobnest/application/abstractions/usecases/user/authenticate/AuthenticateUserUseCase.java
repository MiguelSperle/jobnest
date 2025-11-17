package com.miguel.jobnest.application.abstractions.usecases.user.authenticate;

import com.miguel.jobnest.application.abstractions.usecases.UseCase;
import com.miguel.jobnest.application.usecases.user.authenticate.AuthenticateUserUseCaseInput;
import com.miguel.jobnest.application.usecases.user.authenticate.AuthenticateUserUseCaseOutput;

public interface AuthenticateUserUseCase extends UseCase<AuthenticateUserUseCaseInput, AuthenticateUserUseCaseOutput> {
}
