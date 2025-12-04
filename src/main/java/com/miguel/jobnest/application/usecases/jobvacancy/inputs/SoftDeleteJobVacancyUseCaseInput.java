package com.miguel.jobnest.application.usecases.jobvacancy.inputs;

public record SoftDeleteJobVacancyUseCaseInput(
        String jobVacancyId
) {
    public static SoftDeleteJobVacancyUseCaseInput with(String jobVacancyId) {
        return new SoftDeleteJobVacancyUseCaseInput(jobVacancyId);
    }
}
