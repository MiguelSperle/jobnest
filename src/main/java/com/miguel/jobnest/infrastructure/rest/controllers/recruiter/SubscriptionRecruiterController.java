package com.miguel.jobnest.infrastructure.rest.controllers.recruiter;

import com.miguel.jobnest.application.abstractions.usecases.subscription.ListSubscriptionsByJobVacancyIdUseCase;
import com.miguel.jobnest.application.usecases.subscription.inputs.ListSubscriptionsByJobVacancyIdUseCaseInput;
import com.miguel.jobnest.application.usecases.subscription.outputs.ListSubscriptionsByJobVacancyIdUseCaseOutput;
import com.miguel.jobnest.domain.pagination.Pagination;
import com.miguel.jobnest.domain.pagination.SearchQuery;
import com.miguel.jobnest.infrastructure.rest.dtos.subscription.res.ListSubscriptionsByJobVacancyIdResponse;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/recruiter/subscriptions")
@RequiredArgsConstructor
public class SubscriptionRecruiterController {
    private final ListSubscriptionsByJobVacancyIdUseCase listSubscriptionsByJobVacancyIdUseCase;

    @GetMapping("/{jobVacancyId}")
    @RateLimiter(name = "rateLimitConfiguration")
    public ResponseEntity<Pagination<ListSubscriptionsByJobVacancyIdResponse>> listSubscriptionsByJobVacancyId(
            @PathVariable String jobVacancyId,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "createdAt") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "desc") String direction
    ) {
        final ListSubscriptionsByJobVacancyIdUseCaseOutput output = this.listSubscriptionsByJobVacancyIdUseCase.execute(
                ListSubscriptionsByJobVacancyIdUseCaseInput.with(jobVacancyId, SearchQuery.newSearchQuery(page, perPage, sort, direction)
        ));

        return ResponseEntity.ok().body(ListSubscriptionsByJobVacancyIdResponse.from(output));
    }
}
