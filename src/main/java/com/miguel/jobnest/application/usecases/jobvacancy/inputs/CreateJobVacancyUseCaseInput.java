package com.miguel.jobnest.application.usecases.jobvacancy.inputs;

public record CreateJobVacancyUseCaseInput(
        String title,
        String description,
        String seniorityLevel,
        String modality,
        String companyName
) {
    public static CreateJobVacancyUseCaseInput with(
            final String title,
            final String description,
            final String seniorityLevel,
            final String modality,
            final String companyName
    ) {
        return new CreateJobVacancyUseCaseInput(
                title,
                description,
                seniorityLevel,
                modality,
                companyName
        );
    }
}
