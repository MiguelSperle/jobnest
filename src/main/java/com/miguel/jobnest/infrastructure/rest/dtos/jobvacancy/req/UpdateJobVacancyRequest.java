package com.miguel.jobnest.infrastructure.rest.dtos.jobvacancy.req;

import com.miguel.jobnest.application.usecases.jobvacancy.inputs.UpdateJobVacancyUseCaseInput;
import com.miguel.jobnest.domain.enums.Modality;
import com.miguel.jobnest.domain.enums.SeniorityLevel;
import com.miguel.jobnest.infrastructure.configurations.annotations.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateJobVacancyRequest(
        @NotBlank(message = "Title should not be neither null nor blank")
        @Size(max = 255, message = "Title should not exceed 255 characters")
        String title,

        @NotBlank(message = "Description should not be neither null nor blank")
        @Size(max = 1000, message = "Description should not exceed 1000 characters")
        String description,

        @EnumCheck(enumClass = SeniorityLevel.class, message = "Seniority level should be either INTERN, JUNIOR, INTERMEDIATE, SENIOR or SPECIALIST")
        String seniorityLevel,

        @EnumCheck(enumClass = Modality.class, message = "Modality should be either ON_SITE, HYBRID or REMOTE")
        String modality,

        @NotBlank(message = "Company name should not be neither null nor blank")
        @Size(max = 80, message = "Company name should not exceed 80 characters")
        String companyName
) {
    public UpdateJobVacancyUseCaseInput toInput(String jobVacancyId) {
        return UpdateJobVacancyUseCaseInput.with(
                jobVacancyId,
                this.title,
                this.description,
                this.seniorityLevel,
                this.modality,
                this.companyName
        );
    }
}
