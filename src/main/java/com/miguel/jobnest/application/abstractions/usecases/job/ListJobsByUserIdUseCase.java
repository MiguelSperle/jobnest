package com.miguel.jobnest.application.abstractions.usecases.job;

import com.miguel.jobnest.application.abstractions.usecases.UseCase;
import com.miguel.jobnest.application.usecases.job.inputs.ListJobsByUserIdUseCaseInput;
import com.miguel.jobnest.application.usecases.job.outputs.ListJobsByUserIdUseCaseOutput;

public interface ListJobsByUserIdUseCase extends UseCase<ListJobsByUserIdUseCaseInput, ListJobsByUserIdUseCaseOutput> {
}
