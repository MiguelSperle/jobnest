package com.miguel.jobnest.application.usecases.jobvacancy.inputs;

public record GetJobVacancyByIdUseCaseInput(
        String jobVacancyId
) {
    public static GetJobVacancyByIdUseCaseInput with(final String jobVacancyId) {
        return new GetJobVacancyByIdUseCaseInput(jobVacancyId);
    }
}
