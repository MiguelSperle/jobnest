package com.miguel.jobnest.infrastructure.rest.dtos.jobvacancy.res;

import com.miguel.jobnest.application.usecases.jobvacancy.outputs.ListJobVacanciesUseCaseOutput;
import com.miguel.jobnest.domain.enums.Modality;
import com.miguel.jobnest.domain.enums.SeniorityLevel;
import com.miguel.jobnest.domain.pagination.Pagination;

import java.time.LocalDateTime;

public record ListJobVacanciesResponse(
        String id,
        String title,
        String companyName,
        LocalDateTime createdAt
) {
    public static Pagination<ListJobVacanciesResponse> from(ListJobVacanciesUseCaseOutput output) {
        return output.paginatedJobVacancies().map(paginatedJobVacancy -> new ListJobVacanciesResponse(
                paginatedJobVacancy.getId(),
                paginatedJobVacancy.getTitle(),
                paginatedJobVacancy.getCompanyName(),
                paginatedJobVacancy.getCreatedAt()
        ));
    }
}
