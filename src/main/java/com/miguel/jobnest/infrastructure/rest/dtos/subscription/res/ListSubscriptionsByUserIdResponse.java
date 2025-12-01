package com.miguel.jobnest.infrastructure.rest.dtos.subscription.res;

import com.miguel.jobnest.application.usecases.subscription.outputs.ListSubscriptionsByUserIdUseCaseOutput;
import com.miguel.jobnest.domain.pagination.Pagination;

import java.time.LocalDateTime;

public record ListSubscriptionsByUserIdResponse(
        String id,
        String jobVacancyId,
        String resumeUrl,
        LocalDateTime createdAt
) {
    public static Pagination<ListSubscriptionsByUserIdResponse> from(ListSubscriptionsByUserIdUseCaseOutput output) {
        return output.paginatedSubscriptions().map(paginatedSubscription -> new ListSubscriptionsByUserIdResponse(
                paginatedSubscription.getId(),
                paginatedSubscription.getJobVacancyId(),
                paginatedSubscription.getResumeUrl(),
                paginatedSubscription.getCreatedAt()
        ));
    }
}
