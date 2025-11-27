package com.miguel.jobnest.application.usecases.jobvacancy.inputs;

public record CreateJobVacancyUseCaseInput(
        String userId,
        String title,
        String description,
        String seniorityLevel,
        String modality,
        String companyName
) {
    public static CreateJobVacancyUseCaseInput with(
            String userId,
            String title,
            String description,
            String seniorityLevel,
            String modality,
            String companyName
    ) {
        return new CreateJobVacancyUseCaseInput(
                userId,
                title,
                description,
                seniorityLevel,
                modality,
                companyName
        );
    }
}
