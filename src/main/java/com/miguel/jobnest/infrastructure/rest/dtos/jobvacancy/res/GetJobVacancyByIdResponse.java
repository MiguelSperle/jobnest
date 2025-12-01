package com.miguel.jobnest.infrastructure.rest.dtos.jobvacancy.res;

import com.miguel.jobnest.application.usecases.jobvacancy.outputs.GetJobVacancyByIdUseCaseOutput;
import com.miguel.jobnest.domain.enums.Modality;
import com.miguel.jobnest.domain.enums.SeniorityLevel;

import java.time.LocalDateTime;

public record GetJobVacancyByIdResponse(
        String id,
        String title,
        String description,
        SeniorityLevel seniorityLevel,
        Modality modality,
        String companyName,
        LocalDateTime createdAt
) {
    public static GetJobVacancyByIdResponse from(GetJobVacancyByIdUseCaseOutput output) {
        return new GetJobVacancyByIdResponse(
                output.jobVacancy().getId(),
                output.jobVacancy().getTitle(),
                output.jobVacancy().getDescription(),
                output.jobVacancy().getSeniorityLevel(),
                output.jobVacancy().getModality(),
                output.jobVacancy().getCompanyName(),
                output.jobVacancy().getCreatedAt()
        );
    }
}
