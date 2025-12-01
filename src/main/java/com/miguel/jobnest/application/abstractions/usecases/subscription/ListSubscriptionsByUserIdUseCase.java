package com.miguel.jobnest.application.abstractions.usecases.subscription;

import com.miguel.jobnest.application.abstractions.usecases.UseCase;
import com.miguel.jobnest.application.usecases.subscription.inputs.ListSubscriptionsByUserIdUseCaseInput;
import com.miguel.jobnest.application.usecases.subscription.outputs.ListSubscriptionsByUserIdUseCaseOutput;

public interface ListSubscriptionsByUserIdUseCase extends UseCase<ListSubscriptionsByUserIdUseCaseInput, ListSubscriptionsByUserIdUseCaseOutput> {
}
