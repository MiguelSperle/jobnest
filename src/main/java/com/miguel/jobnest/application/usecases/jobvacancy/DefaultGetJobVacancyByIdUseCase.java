package com.miguel.jobnest.application.usecases.jobvacancy;

import com.miguel.jobnest.application.abstractions.repositories.JobVacancyRepository;
import com.miguel.jobnest.application.abstractions.usecases.jobvacancy.GetJobVacancyByIdUseCase;
import com.miguel.jobnest.application.usecases.jobvacancy.inputs.GetJobVacancyByIdUseCaseInput;
import com.miguel.jobnest.application.usecases.jobvacancy.outputs.GetJobVacancyByIdUseCaseOutput;
import com.miguel.jobnest.domain.entities.JobVacancy;
import com.miguel.jobnest.domain.exceptions.NotFoundException;

public class DefaultGetJobVacancyByIdUseCase implements GetJobVacancyByIdUseCase {
    private final JobVacancyRepository jobVacancyRepository;

    public DefaultGetJobVacancyByIdUseCase(JobVacancyRepository jobVacancyRepository) {
        this.jobVacancyRepository = jobVacancyRepository;
    }

    @Override
    public GetJobVacancyByIdUseCaseOutput execute(GetJobVacancyByIdUseCaseInput input) {
        final JobVacancy jobVacancy = this.getJobVacancyById(input.jobVacancyId());

        return GetJobVacancyByIdUseCaseOutput.from(jobVacancy);
    }

    private JobVacancy getJobVacancyById(String id) {
        return this.jobVacancyRepository.findById(id).orElseThrow(() -> NotFoundException.with("Job vacancy not found"));
    }
}
