package com.miguel.jobnest.infrastructure.abstractions.rest.controllers.recruiter;

import com.miguel.jobnest.domain.pagination.Pagination;
import com.miguel.jobnest.infrastructure.rest.dtos.subscription.res.ListSubscriptionsByJobVacancyIdResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1/recruiter/subscriptions")
public interface SubscriptionRecruiterControllerAPI {
    @GetMapping("/{jobVacancyId}")
    ResponseEntity<Pagination<ListSubscriptionsByJobVacancyIdResponse>> listSubscriptionsByJobVacancyId(
            @PathVariable String jobVacancyId,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "createdAt") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "desc") String direction
    );
}
