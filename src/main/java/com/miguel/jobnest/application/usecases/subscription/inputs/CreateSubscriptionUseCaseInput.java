package com.miguel.jobnest.application.usecases.subscription.inputs;

public record CreateSubscriptionUseCaseInput(
        String userId,
        byte[] bytesFile,
        String jobVacancyId
) {
    public static CreateSubscriptionUseCaseInput with(
            final String userId,
            final byte[] bytesFile,
            final String jobVacancyId
    ) {
        return new CreateSubscriptionUseCaseInput(
                userId,
                bytesFile,
                jobVacancyId
        );
    }
}
