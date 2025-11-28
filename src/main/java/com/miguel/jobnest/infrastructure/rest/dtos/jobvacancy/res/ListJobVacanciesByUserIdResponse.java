package com.miguel.jobnest.infrastructure.rest.dtos.jobvacancy.res;

import com.miguel.jobnest.application.usecases.jobvacancy.outputs.ListJobVacanciesByUserIdUseCaseOutput;
import com.miguel.jobnest.domain.enums.Modality;
import com.miguel.jobnest.domain.enums.SeniorityLevel;
import com.miguel.jobnest.domain.pagination.Pagination;

import java.time.LocalDateTime;

public record ListJobVacanciesByUserIdResponse(
        String id,
        String title,
        String description,
        SeniorityLevel seniorityLevel,
        Modality modality,
        String companyName,
        LocalDateTime createdAt
) {
    public static Pagination<ListJobVacanciesByUserIdResponse> from(ListJobVacanciesByUserIdUseCaseOutput output) {
        return output.paginatedJobs().map(paginatedJob -> new ListJobVacanciesByUserIdResponse(
                paginatedJob.getId(),
                paginatedJob.getTitle(),
                paginatedJob.getDescription(),
                paginatedJob.getSeniorityLevel(),
                paginatedJob.getModality(),
                paginatedJob.getCompanyName(),
                paginatedJob.getCreatedAt()
        ));
    }
}
