package com.miguel.jobnest.application.usecases.jobvacancy.inputs;

public record SoftDeleteJobVacancyUseCaseInput(
        String id
) {
    public static SoftDeleteJobVacancyUseCaseInput with(String id) {
        return new SoftDeleteJobVacancyUseCaseInput(id);
    }
}
