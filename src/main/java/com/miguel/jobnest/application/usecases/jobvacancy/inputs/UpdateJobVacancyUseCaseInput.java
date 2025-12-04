package com.miguel.jobnest.application.usecases.jobvacancy.inputs;

public record UpdateJobVacancyUseCaseInput(
        String jobVacancyId,
        String title,
        String description,
        String seniorityLevel,
        String modality,
        String companyName
) {
    public static UpdateJobVacancyUseCaseInput with(
            String jobVacancyId,
            String title,
            String description,
            String seniorityLevel,
            String modality,
            String companyName
    ) {
        return new UpdateJobVacancyUseCaseInput(
                jobVacancyId,
                title,
                description,
                seniorityLevel,
                modality,
                companyName
        );
    }
}
