package com.miguel.jobnest.infrastructure.rest.controllers;

import com.miguel.jobnest.application.abstractions.usecases.subscription.CreateSubscriptionUseCase;
import com.miguel.jobnest.infrastructure.rest.dtos.common.res.MessageResponse;
import com.miguel.jobnest.infrastructure.rest.dtos.subscription.req.CreateSubscriptionRequest;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
    private final CreateSubscriptionUseCase createSubscriptionUseCase;

    @PostMapping
    @RateLimiter(name = "rateLimitConfiguration")
    public ResponseEntity<MessageResponse> createSubscription(@RequestPart CreateSubscriptionRequest request, @RequestPart MultipartFile file) throws IOException {
        this.createSubscriptionUseCase.execute(request.toInput(file.getBytes()));

        return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.from("Subscription created successfully"));
    }
}
