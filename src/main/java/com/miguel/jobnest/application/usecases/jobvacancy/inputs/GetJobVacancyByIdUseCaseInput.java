package com.miguel.jobnest.application.usecases.jobvacancy.inputs;

public record GetJobVacancyByIdUseCaseInput(
        String jobVacancyId
) {
    public static GetJobVacancyByIdUseCaseInput with(String jobVacancyId) {
        return new GetJobVacancyByIdUseCaseInput(jobVacancyId);
    }
}
