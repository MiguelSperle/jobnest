package com.miguel.jobnest.infrastructure.rest.controllers;

import com.miguel.jobnest.application.abstractions.usecases.jobvacancy.CreateJobVacancyUseCase;
import com.miguel.jobnest.application.abstractions.usecases.jobvacancy.ListJobVacanciesByUserIdUseCase;
import com.miguel.jobnest.application.abstractions.usecases.jobvacancy.ListJobVacanciesUseCase;
import com.miguel.jobnest.application.abstractions.usecases.jobvacancy.UpdateJobVacancyUseCase;
import com.miguel.jobnest.application.usecases.jobvacancy.inputs.ListJobVacanciesByUserIdUseCaseInput;
import com.miguel.jobnest.application.usecases.jobvacancy.inputs.ListJobVacanciesUseCaseInput;
import com.miguel.jobnest.application.usecases.jobvacancy.outputs.ListJobVacanciesByUserIdUseCaseOutput;
import com.miguel.jobnest.application.usecases.jobvacancy.outputs.ListJobVacanciesUseCaseOutput;
import com.miguel.jobnest.domain.pagination.Pagination;
import com.miguel.jobnest.domain.pagination.SearchQuery;
import com.miguel.jobnest.infrastructure.rest.dtos.common.res.MessageResponse;
import com.miguel.jobnest.infrastructure.rest.dtos.job.req.CreateJobRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.job.req.UpdateJobRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.job.res.ListJobsByUserIdResponse;
import com.miguel.jobnest.infrastructure.rest.dtos.job.res.ListJobsResponse;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobVacancyController {
    private final CreateJobVacancyUseCase createJobVacancyUseCase;
    private final ListJobVacanciesByUserIdUseCase listJobVacanciesByUserIdUseCase;
    private final ListJobVacanciesUseCase listJobVacanciesUseCase;
    private final UpdateJobVacancyUseCase updateJobVacancyUseCase;

    @PostMapping
    @RateLimiter(name = "rateLimitConfiguration")
    public ResponseEntity<MessageResponse> createJob(@RequestBody @Valid CreateJobRequest request) {
        this.createJobVacancyUseCase.execute(request.toInput());

        return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.from("Job created successfully"));
    }

    @GetMapping("/{userId}")
    @RateLimiter(name = "rateLimitConfiguration")
    public ResponseEntity<Pagination<ListJobsByUserIdResponse>> listJobsByUserId(
            @PathVariable String userId,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") int perPage,
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "sort", required = false, defaultValue = "createdAt") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "desc") String direction
    ) {
        final ListJobVacanciesByUserIdUseCaseOutput output = this.listJobVacanciesByUserIdUseCase.execute(
                ListJobVacanciesByUserIdUseCaseInput.with(userId, SearchQuery.newSearchQuery(page, perPage, search, sort, direction))
        );

        return ResponseEntity.ok().body(ListJobsByUserIdResponse.from(output));
    }

    @PatchMapping("/{id}")
    @RateLimiter(name = "rateLimitConfiguration")
    public ResponseEntity<MessageResponse> updateJob(
            @PathVariable String id,
            @RequestBody @Valid UpdateJobRequest request
    ) {
        this.updateJobVacancyUseCase.execute(request.toInput(id));

        return ResponseEntity.ok().body(MessageResponse.from("Job updated successfully"));
    }

    @GetMapping
    @RateLimiter(name = "rateLimitConfiguration")
    public ResponseEntity<Pagination<ListJobsResponse>> listJobs(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") int perPage,
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "sort", required = false, defaultValue = "createdAt") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "desc") String direction
    ) {
        final ListJobVacanciesUseCaseOutput output = this.listJobVacanciesUseCase.execute(ListJobVacanciesUseCaseInput.with(
                SearchQuery.newSearchQuery(page, perPage, search, sort, direction)
        ));

        return ResponseEntity.ok().body(ListJobsResponse.from(output));
    }
}
