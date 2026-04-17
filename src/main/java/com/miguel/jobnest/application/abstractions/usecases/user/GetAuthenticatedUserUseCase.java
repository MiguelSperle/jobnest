package com.miguel.jobnest.application.abstractions.usecases.user;

import com.miguel.jobnest.application.abstractions.usecases.UseCase;
import com.miguel.jobnest.application.usecases.user.inputs.GetAuthenticatedUserUseCaseInput;
import com.miguel.jobnest.application.usecases.user.outputs.GetAuthenticatedUserUseCaseOutput;

public interface GetAuthenticatedUserUseCase extends UseCase<GetAuthenticatedUserUseCaseInput, GetAuthenticatedUserUseCaseOutput> {
}
