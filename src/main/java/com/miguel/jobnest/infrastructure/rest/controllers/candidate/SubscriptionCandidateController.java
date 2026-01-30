package com.miguel.jobnest.infrastructure.rest.controllers.candidate;

import com.miguel.jobnest.application.abstractions.usecases.subscription.CreateSubscriptionUseCase;
import com.miguel.jobnest.application.abstractions.usecases.subscription.ListSubscriptionsByUserIdUseCase;
import com.miguel.jobnest.application.abstractions.usecases.subscription.CancelSubscriptionUseCase;
import com.miguel.jobnest.application.usecases.subscription.inputs.ListSubscriptionsByUserIdUseCaseInput;
import com.miguel.jobnest.application.usecases.subscription.inputs.CancelSubscriptionUseCaseInput;
import com.miguel.jobnest.application.usecases.subscription.outputs.ListSubscriptionsByUserIdUseCaseOutput;
import com.miguel.jobnest.domain.pagination.Pagination;
import com.miguel.jobnest.domain.pagination.SearchQuery;
import com.miguel.jobnest.infrastructure.idempotency.IdempotencyKey;
import com.miguel.jobnest.infrastructure.rest.dtos.MessageResponse;
import com.miguel.jobnest.infrastructure.rest.dtos.subscription.req.CreateSubscriptionRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.subscription.res.ListSubscriptionsByUserIdResponse;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/candidate/subscriptions")
@RequiredArgsConstructor
public class SubscriptionCandidateController {
    private final CreateSubscriptionUseCase createSubscriptionUseCase;
    private final ListSubscriptionsByUserIdUseCase listSubscriptionsByUserIdUseCase;
    private final CancelSubscriptionUseCase cancelSubscriptionUseCase;

    @PostMapping
    @RateLimiter(name = "rateLimitConfiguration")
    @IdempotencyKey
    public ResponseEntity<MessageResponse> createSubscription(@RequestPart CreateSubscriptionRequest request, @RequestPart MultipartFile resumeFile) throws IOException {
        this.createSubscriptionUseCase.execute(request.toInput(resumeFile.getBytes()));

        return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.from("Subscription created successfully"));
    }

    @GetMapping
    @RateLimiter(name = "rateLimitConfiguration")
    public ResponseEntity<Pagination<ListSubscriptionsByUserIdResponse>> listSubscriptionsByUserId(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "createdAt") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "desc") String direction
    ) {
        final ListSubscriptionsByUserIdUseCaseOutput output = this.listSubscriptionsByUserIdUseCase.execute(
                ListSubscriptionsByUserIdUseCaseInput.with(SearchQuery.newSearchQuery(page, perPage, sort, direction))
        );

        return ResponseEntity.ok().body(ListSubscriptionsByUserIdResponse.from(output));
    }

    @PatchMapping("/{subscriptionId}")
    @RateLimiter(name = "rateLimitConfiguration")
    public ResponseEntity<MessageResponse> cancelSubscription(@PathVariable String subscriptionId) {
        this.cancelSubscriptionUseCase.execute(CancelSubscriptionUseCaseInput.with(subscriptionId));

        return ResponseEntity.ok().body(MessageResponse.from("Subscription canceled successfully"));
    }
}
