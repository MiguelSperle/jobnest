package com.miguel.jobnest.infrastructure.rest.dtos.job.res;

import com.miguel.jobnest.application.usecases.job.outputs.ListJobsByUserIdUseCaseOutput;
import com.miguel.jobnest.domain.enums.Modality;
import com.miguel.jobnest.domain.enums.SeniorityLevel;
import com.miguel.jobnest.domain.pagination.Pagination;

import java.time.LocalDateTime;

public record ListJobsByUserIdResponse(
        String id,
        String title,
        String description,
        SeniorityLevel seniorityLevel,
        Modality modality,
        LocalDateTime createdAt
) {
    public static Pagination<ListJobsByUserIdResponse> from(ListJobsByUserIdUseCaseOutput output) {
        return output.paginatedJobs().map(paginatedJob -> new ListJobsByUserIdResponse(
                paginatedJob.getId(),
                paginatedJob.getTitle(),
                paginatedJob.getDescription(),
                paginatedJob.getSeniorityLevel(),
                paginatedJob.getModality(),
                paginatedJob.getCreatedAt()
        ));
    }
}
