package com.miguel.jobnest.infrastructure.rest.dtos.subscription.res;

import com.miguel.jobnest.application.usecases.subscription.outputs.ListSubscriptionsByJobVacancyIdUseCaseOutput;
import com.miguel.jobnest.domain.pagination.Pagination;

import java.time.LocalDateTime;

public record ListSubscriptionsByJobVacancyIdResponse(
        String id,
        String jobVacancyId,
        String resumeUrl,
        LocalDateTime createdAt
) {
    public static Pagination<ListSubscriptionsByJobVacancyIdResponse> from(ListSubscriptionsByJobVacancyIdUseCaseOutput output) {
        return output.paginatedSubscriptions().map(paginatedSubscription -> new ListSubscriptionsByJobVacancyIdResponse(
                paginatedSubscription.getId(),
                paginatedSubscription.getJobVacancyId(),
                paginatedSubscription.getResumeUrl(),
                paginatedSubscription.getCreatedAt()
        ));
    }
}
