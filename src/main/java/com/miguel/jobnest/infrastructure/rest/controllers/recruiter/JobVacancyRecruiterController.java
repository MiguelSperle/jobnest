package com.miguel.jobnest.infrastructure.rest.controllers.recruiter;

import com.miguel.jobnest.application.abstractions.usecases.jobvacancy.*;
import com.miguel.jobnest.application.usecases.jobvacancy.inputs.ListJobVacanciesByUserIdUseCaseInput;
import com.miguel.jobnest.application.usecases.jobvacancy.inputs.SoftDeleteJobVacancyUseCaseInput;
import com.miguel.jobnest.application.usecases.jobvacancy.outputs.ListJobVacanciesByUserIdUseCaseOutput;
import com.miguel.jobnest.domain.pagination.Pagination;
import com.miguel.jobnest.domain.pagination.SearchQuery;
import com.miguel.jobnest.infrastructure.rest.dtos.common.res.MessageResponse;
import com.miguel.jobnest.infrastructure.rest.dtos.jobvacancy.req.CreateJobVacancyRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.jobvacancy.req.UpdateJobVacancyRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.jobvacancy.res.ListJobVacanciesByUserIdResponse;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/recruiter/job-vacancies")
@RequiredArgsConstructor
public class JobVacancyRecruiterController {
    private final CreateJobVacancyUseCase createJobVacancyUseCase;
    private final ListJobVacanciesByUserIdUseCase listJobVacanciesByUserIdUseCase;
    private final UpdateJobVacancyUseCase updateJobVacancyUseCase;
    private final SoftDeleteJobVacancyUseCase softDeleteJobVacancyUseCase;

    @PostMapping
    @RateLimiter(name = "rateLimitConfiguration")
    public ResponseEntity<MessageResponse> createJobVacancy(@RequestBody @Valid CreateJobVacancyRequest request) {
        this.createJobVacancyUseCase.execute(request.toInput());

        return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.from("Job vacancy created successfully"));
    }

    @GetMapping
    @RateLimiter(name = "rateLimitConfiguration")
    public ResponseEntity<Pagination<ListJobVacanciesByUserIdResponse>> listJobVacanciesByUserId(
            @PathVariable String userId,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") int perPage,
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "sort", required = false, defaultValue = "createdAt") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "desc") String direction,
            @RequestParam Map<String, String> filters
    ) {
        final ListJobVacanciesByUserIdUseCaseOutput output = this.listJobVacanciesByUserIdUseCase.execute(
                ListJobVacanciesByUserIdUseCaseInput.with(SearchQuery.newSearchQuery(page, perPage, search, sort, direction, filters))
        );

        return ResponseEntity.ok().body(ListJobVacanciesByUserIdResponse.from(output));
    }

    @PatchMapping("/{jobVacancyId}")
    @RateLimiter(name = "rateLimitConfiguration")
    public ResponseEntity<MessageResponse> updateJobVacancy(
            @PathVariable String jobVacancyId,
            @RequestBody @Valid UpdateJobVacancyRequest request
    ) {
        this.updateJobVacancyUseCase.execute(request.toInput(jobVacancyId));

        return ResponseEntity.ok().body(MessageResponse.from("Job vacancy updated successfully"));
    }

    @DeleteMapping("/{jobVacancyId}")
    @RateLimiter(name = "rateLimitConfiguration")
    public ResponseEntity<MessageResponse> deleteJobVacancy(@PathVariable String jobVacancyId) {
        this.softDeleteJobVacancyUseCase.execute(SoftDeleteJobVacancyUseCaseInput.with(jobVacancyId));

        return ResponseEntity.ok().body(MessageResponse.from("Job vacancy deleted successfully"));
    }
}
