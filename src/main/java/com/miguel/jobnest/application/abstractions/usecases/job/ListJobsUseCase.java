package com.miguel.jobnest.application.abstractions.usecases.job;

import com.miguel.jobnest.application.abstractions.usecases.UseCase;
import com.miguel.jobnest.application.usecases.job.inputs.ListJobsUseCaseInput;
import com.miguel.jobnest.application.usecases.job.outputs.ListJobsUseCaseOutput;

public interface ListJobsUseCase extends UseCase<ListJobsUseCaseInput, ListJobsUseCaseOutput> {
}
