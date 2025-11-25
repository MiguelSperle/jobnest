package com.miguel.jobnest.application.usecases.job.inputs;

public record CreateJobUseCaseInput(
        String userId,
        String title,
        String description,
        String seniorityLevel,
        String modality
) {
    public static CreateJobUseCaseInput with(
            String userId,
            String title,
            String description,
            String seniorityLevel,
            String modality
    ) {
        return new CreateJobUseCaseInput(
                userId,
                title,
                description,
                seniorityLevel,
                modality
        );
    }
}
