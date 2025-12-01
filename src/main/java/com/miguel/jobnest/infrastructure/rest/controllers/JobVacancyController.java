package com.miguel.jobnest.infrastructure.rest.controllers;

import com.miguel.jobnest.application.abstractions.usecases.jobvacancy.*;
import com.miguel.jobnest.application.usecases.jobvacancy.inputs.GetJobVacancyByIdUseCaseInput;
import com.miguel.jobnest.application.usecases.jobvacancy.outputs.GetJobVacancyByIdUseCaseOutput;
import com.miguel.jobnest.infrastructure.rest.dtos.jobvacancy.res.GetJobVacancyByIdResponse;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/job-vacancies")
@RequiredArgsConstructor
public class JobVacancyController {
    private final GetJobVacancyByIdUseCase getJobVacancyByIdUseCase;

    @GetMapping("/{id}")
    @RateLimiter(name = "rateLimitConfiguration")
    public ResponseEntity<GetJobVacancyByIdResponse> getJobVacancyById(@PathVariable String id) {
        final GetJobVacancyByIdUseCaseOutput output = this.getJobVacancyByIdUseCase.execute(GetJobVacancyByIdUseCaseInput.with(id));

        return ResponseEntity.ok().body(GetJobVacancyByIdResponse.from(output));
    }
}
