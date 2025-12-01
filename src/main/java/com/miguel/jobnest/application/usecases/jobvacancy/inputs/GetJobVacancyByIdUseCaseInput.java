package com.miguel.jobnest.application.usecases.jobvacancy.inputs;

public record GetJobVacancyByIdUseCaseInput(
        String id
) {
    public static GetJobVacancyByIdUseCaseInput with(String id) {
        return new GetJobVacancyByIdUseCaseInput(id);
    }
}
