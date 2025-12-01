package com.miguel.jobnest.application.abstractions.usecases.jobvacancy;

import com.miguel.jobnest.application.abstractions.usecases.UseCase;
import com.miguel.jobnest.application.usecases.jobvacancy.inputs.GetJobVacancyByIdUseCaseInput;
import com.miguel.jobnest.application.usecases.jobvacancy.outputs.GetJobVacancyByIdUseCaseOutput;

public interface GetJobVacancyByIdUseCase extends UseCase<GetJobVacancyByIdUseCaseInput, GetJobVacancyByIdUseCaseOutput> {
}
