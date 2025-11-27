package com.miguel.jobnest.application.usecases.subscription.inputs;

public record CreateSubscriptionUseCaseInput(
        byte[] bytesFile,
        String userId,
        String jobVacancyId
) {
    public static CreateSubscriptionUseCaseInput with(
            byte[] bytesFile,
            String userId,
            String jobVacancyId
    ) {
        return new CreateSubscriptionUseCaseInput(
                bytesFile,
                userId,
                jobVacancyId
        );
    }
}
