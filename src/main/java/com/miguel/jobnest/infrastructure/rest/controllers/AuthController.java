package com.miguel.jobnest.infrastructure.rest.controllers;

import com.miguel.jobnest.application.abstractions.usecases.user.RegisterUserUseCase;
import com.miguel.jobnest.infrastructure.rest.dtos.req.RegisterUserRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.res.MessageResponse;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final RegisterUserUseCase registerUserUseCase;

    @PostMapping("/register")
    @RateLimiter(name = "rateLimitConfiguration")
    public ResponseEntity<MessageResponse> registerUser(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        this.registerUserUseCase.execute(registerUserRequest.toInput());

        return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.from("User registered successfully"));
    }
}
