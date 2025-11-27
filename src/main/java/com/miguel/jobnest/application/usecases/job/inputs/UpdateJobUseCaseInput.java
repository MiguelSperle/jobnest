package com.miguel.jobnest.application.usecases.job.inputs;

public record UpdateJobUseCaseInput(
        String id,
        String title,
        String description,
        String seniorityLevel,
        String modality
) {
    public static UpdateJobUseCaseInput with(
            String id,
            String title,
            String description,
            String seniorityLevel,
            String modality
    ) {
        return new UpdateJobUseCaseInput(
                id,
                title,
                description,
                seniorityLevel,
                modality
        );
    }
}
