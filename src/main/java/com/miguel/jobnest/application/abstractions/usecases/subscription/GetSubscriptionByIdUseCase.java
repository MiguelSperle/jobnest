package com.miguel.jobnest.application.abstractions.usecases.subscription;

import com.miguel.jobnest.application.abstractions.usecases.UseCase;
import com.miguel.jobnest.application.usecases.subscription.inputs.GetSubscriptionByIdUseCaseInput;
import com.miguel.jobnest.application.usecases.subscription.outputs.GetSubscriptionByIdUseCaseOutput;

public interface GetSubscriptionByIdUseCase extends UseCase<GetSubscriptionByIdUseCaseInput, GetSubscriptionByIdUseCaseOutput> {
}
