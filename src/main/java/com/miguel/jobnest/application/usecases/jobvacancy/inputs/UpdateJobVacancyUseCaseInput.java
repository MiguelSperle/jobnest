package com.miguel.jobnest.application.usecases.jobvacancy.inputs;

public record UpdateJobVacancyUseCaseInput(
        String id,
        String title,
        String description,
        String seniorityLevel,
        String modality,
        String companyName
) {
    public static UpdateJobVacancyUseCaseInput with(
            String id,
            String title,
            String description,
            String seniorityLevel,
            String modality,
            String companyName
    ) {
        return new UpdateJobVacancyUseCaseInput(
                id,
                title,
                description,
                seniorityLevel,
                modality,
                companyName
        );
    }
}
