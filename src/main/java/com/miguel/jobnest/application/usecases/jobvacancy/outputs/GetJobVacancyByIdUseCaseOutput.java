package com.miguel.jobnest.application.usecases.jobvacancy.outputs;

import com.miguel.jobnest.domain.entities.JobVacancy;

public record GetJobVacancyByIdUseCaseOutput(
        JobVacancy jobVacancy
) {
    public static GetJobVacancyByIdUseCaseOutput from(JobVacancy jobVacancy) {
        return new GetJobVacancyByIdUseCaseOutput(jobVacancy);
    }
}
