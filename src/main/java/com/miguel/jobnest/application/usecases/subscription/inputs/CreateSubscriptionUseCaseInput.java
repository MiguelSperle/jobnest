package com.miguel.jobnest.application.usecases.subscription.inputs;

public record CreateSubscriptionUseCaseInput(
        byte[] bytesFile,
        String jobVacancyId
) {
    public static CreateSubscriptionUseCaseInput with(
            byte[] bytesFile,
            String jobVacancyId
    ) {
        return new CreateSubscriptionUseCaseInput(
                bytesFile,
                jobVacancyId
        );
    }
}
