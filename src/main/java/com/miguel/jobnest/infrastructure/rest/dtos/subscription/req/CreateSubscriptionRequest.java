package com.miguel.jobnest.infrastructure.rest.dtos.subscription.req;

import com.miguel.jobnest.application.usecases.subscription.inputs.CreateSubscriptionUseCaseInput;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateSubscriptionRequest(
        @NotBlank(message = "Job vacancy id should not be neither null nor blank")
        @Size(min = 36, max = 36, message = "Job vacancy id should have exactly 36 characters")
        String jobVacancyId
) {
    public CreateSubscriptionUseCaseInput toInput(byte[] bytesFile) {
        return CreateSubscriptionUseCaseInput.with(
                bytesFile,
                this.jobVacancyId
        );
    }
}
