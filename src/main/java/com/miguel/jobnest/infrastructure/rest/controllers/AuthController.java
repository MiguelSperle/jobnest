package com.miguel.jobnest.infrastructure.rest.controllers;

import com.miguel.jobnest.application.abstractions.usecases.user.AuthenticateUserUseCase;
import com.miguel.jobnest.application.abstractions.usecases.user.RegisterUserUseCase;
import com.miguel.jobnest.application.usecases.user.outputs.AuthenticateUserUseCaseOutput;
import com.miguel.jobnest.infrastructure.rest.dtos.user.requests.AuthenticateUserRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.user.requests.RegisterUserRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.common.responses.MessageResponse;
import com.miguel.jobnest.infrastructure.rest.dtos.user.responses.AuthenticateUserResponse;
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
    private final AuthenticateUserUseCase authenticateUserUseCase;

    @PostMapping("/register")
    @RateLimiter(name = "rateLimitConfiguration")
    public ResponseEntity<MessageResponse> registerUser(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        this.registerUserUseCase.execute(registerUserRequest.toInput());

        return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.from("User registered successfully"));
    }

    @PostMapping("/login")
    @RateLimiter(name = "rateLimitConfiguration")
    public ResponseEntity<AuthenticateUserResponse> authenticateUser(@RequestBody @Valid AuthenticateUserRequest authenticateUserRequest) {
        final AuthenticateUserUseCaseOutput authenticateUserUseCaseOutput =
                this.authenticateUserUseCase.execute(authenticateUserRequest.toInput());

        return ResponseEntity.ok().body(AuthenticateUserResponse.from(authenticateUserUseCaseOutput));
    }
}
