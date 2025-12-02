package com.miguel.jobnest.infrastructure.rest.dtos.subscription.res;

import com.miguel.jobnest.application.usecases.subscription.outputs.GetSubscriptionByIdUseCaseOutput;

import java.time.LocalDateTime;

public record GetSubscriptionByIdResponse(
        String id,
        String jobVacancyId,
        String resumeUrl,
        Boolean isCanceled,
        LocalDateTime createdAt
) {
    public static GetSubscriptionByIdResponse from(GetSubscriptionByIdUseCaseOutput output) {
        return new GetSubscriptionByIdResponse(
                output.subscription().getId(),
                output.subscription().getJobVacancyId(),
                output.subscription().getResumeUrl(),
                output.subscription().getIsCanceled(),
                output.subscription().getCreatedAt()
        );
    }
}
