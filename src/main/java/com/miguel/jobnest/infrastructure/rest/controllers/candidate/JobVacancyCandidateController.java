package com.miguel.jobnest.infrastructure.rest.controllers.candidate;

import com.miguel.jobnest.application.abstractions.usecases.jobvacancy.ListJobVacanciesUseCase;
import com.miguel.jobnest.application.usecases.jobvacancy.inputs.ListJobVacanciesUseCaseInput;
import com.miguel.jobnest.application.usecases.jobvacancy.outputs.ListJobVacanciesUseCaseOutput;
import com.miguel.jobnest.domain.pagination.Pagination;
import com.miguel.jobnest.domain.pagination.SearchQuery;
import com.miguel.jobnest.infrastructure.rest.dtos.jobvacancy.res.ListJobVacanciesResponse;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/candidate/job-vacancies")
@RequiredArgsConstructor
public class JobVacancyCandidateController {
    private final ListJobVacanciesUseCase listJobVacanciesUseCase;

    @GetMapping
    @RateLimiter(name = "rateLimitConfiguration")
    public ResponseEntity<Pagination<ListJobVacanciesResponse>> listJobVacancies(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") int perPage,
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "sort", required = false, defaultValue = "createdAt") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "desc") String direction
    ) {
        final ListJobVacanciesUseCaseOutput output = this.listJobVacanciesUseCase.execute(ListJobVacanciesUseCaseInput.with(
                SearchQuery.newSearchQuery(page, perPage, search, sort, direction)
        ));

        return ResponseEntity.ok().body(ListJobVacanciesResponse.from(output));
    }
}
