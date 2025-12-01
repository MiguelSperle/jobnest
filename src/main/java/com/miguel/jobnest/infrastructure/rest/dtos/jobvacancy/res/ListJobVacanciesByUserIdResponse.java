package com.miguel.jobnest.infrastructure.rest.dtos.jobvacancy.res;

import com.miguel.jobnest.application.usecases.jobvacancy.outputs.ListJobVacanciesByUserIdUseCaseOutput;
import com.miguel.jobnest.domain.pagination.Pagination;

import java.time.LocalDateTime;

public record ListJobVacanciesByUserIdResponse(
        String id,
        String title,
        String companyName,
        Boolean isDeleted,
        LocalDateTime createdAt
) {
    public static Pagination<ListJobVacanciesByUserIdResponse> from(ListJobVacanciesByUserIdUseCaseOutput output) {
        return output.paginatedJobVacancies().map(paginatedJobVacancy -> new ListJobVacanciesByUserIdResponse(
                paginatedJobVacancy.getId(),
                paginatedJobVacancy.getTitle(),
                paginatedJobVacancy.getCompanyName(),
                paginatedJobVacancy.getIsDeleted(),
                paginatedJobVacancy.getCreatedAt()
        ));
    }
}
