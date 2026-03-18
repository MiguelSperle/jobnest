package com.miguel.jobnest.infrastructure.abstractions.rest.controllers.candidate;

import com.miguel.jobnest.domain.pagination.Pagination;
import com.miguel.jobnest.infrastructure.rest.dtos.MessageResponse;
import com.miguel.jobnest.infrastructure.rest.dtos.subscription.req.CreateSubscriptionRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.subscription.res.ListSubscriptionsByUserIdResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/api/v1/candidate/subscriptions")
public interface SubscriptionCandidateControllerAPI {
    @PostMapping
    ResponseEntity<MessageResponse> createSubscription(@RequestPart CreateSubscriptionRequest request, @RequestPart MultipartFile resumeFile) throws IOException;

    @GetMapping
    ResponseEntity<Pagination<ListSubscriptionsByUserIdResponse>> listSubscriptionsByUserId(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "createdAt") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "desc") String direction
    );

    @PatchMapping("/{subscriptionId}")
    ResponseEntity<MessageResponse> cancelSubscription(@PathVariable String subscriptionId);
}
