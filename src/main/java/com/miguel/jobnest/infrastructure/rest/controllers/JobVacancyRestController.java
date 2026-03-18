package com.miguel.jobnest.infrastructure.rest.controllers;

import com.miguel.jobnest.application.abstractions.usecases.jobvacancy.*;
import com.miguel.jobnest.application.usecases.jobvacancy.inputs.GetJobVacancyByIdUseCaseInput;
import com.miguel.jobnest.application.usecases.jobvacancy.outputs.GetJobVacancyByIdUseCaseOutput;
import com.miguel.jobnest.infrastructure.abstractions.rest.controllers.JobVacancyControllerAPI;
import com.miguel.jobnest.infrastructure.rest.dtos.jobvacancy.res.GetJobVacancyByIdResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class JobVacancyRestController implements JobVacancyControllerAPI {
    private final GetJobVacancyByIdUseCase getJobVacancyByIdUseCase;

    public JobVacancyRestController(final GetJobVacancyByIdUseCase getJobVacancyByIdUseCase) {
        this.getJobVacancyByIdUseCase = getJobVacancyByIdUseCase;
    }

    @Override
    public ResponseEntity<GetJobVacancyByIdResponse> getJobVacancyById(final String jobVacancyId) {
        final GetJobVacancyByIdUseCaseOutput output = this.getJobVacancyByIdUseCase.execute(GetJobVacancyByIdUseCaseInput.with(jobVacancyId));

        return ResponseEntity.ok().body(GetJobVacancyByIdResponse.from(output));
    }
}
