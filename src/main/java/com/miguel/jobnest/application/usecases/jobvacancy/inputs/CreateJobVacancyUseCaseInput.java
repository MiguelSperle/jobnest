package com.miguel.jobnest.application.usecases.jobvacancy.inputs;

public record CreateJobVacancyUseCaseInput(
        String title,
        String description,
        String seniorityLevel,
        String modality,
        String companyName
) {
    public static CreateJobVacancyUseCaseInput with(
            String title,
            String description,
            String seniorityLevel,
            String modality,
            String companyName
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
