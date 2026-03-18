package com.miguel.jobnest.infrastructure.abstractions.rest.controllers;

import com.miguel.jobnest.infrastructure.rest.dtos.jobvacancy.res.GetJobVacancyByIdResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/job-vacancies")
public interface JobVacancyControllerAPI {
    @GetMapping("/{jobVacancyId}")
    ResponseEntity<GetJobVacancyByIdResponse> getJobVacancyById(@PathVariable String jobVacancyId);
}
