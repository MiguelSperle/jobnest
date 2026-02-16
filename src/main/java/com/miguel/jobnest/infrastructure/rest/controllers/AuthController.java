package com.miguel.jobnest.infrastructure.rest.controllers;

import com.miguel.jobnest.application.abstractions.usecases.user.AuthenticateUserUseCase;
import com.miguel.jobnest.application.abstractions.usecases.user.CreateUserUseCase;
import com.miguel.jobnest.application.usecases.user.outputs.AuthenticateUserUseCaseOutput;
import com.miguel.jobnest.infrastructure.idempotency.Idempotency;
import com.miguel.jobnest.infrastructure.rest.dtos.user.req.AuthenticateUserRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.user.req.CreateUserRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.MessageResponse;
import com.miguel.jobnest.infrastructure.rest.dtos.user.res.AuthenticateUserResponse;
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
    private final CreateUserUseCase createUserUseCase;
    private final AuthenticateUserUseCase authenticateUserUseCase;

    @PostMapping("/create")
    @RateLimiter(name = "rateLimitConfiguration")
    @Idempotency
    public ResponseEntity<MessageResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        this.createUserUseCase.execute(request.toInput());

        return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.from("User created successfully"));
    }

    @PostMapping("/authenticate")
    @RateLimiter(name = "rateLimitConfiguration")
    public ResponseEntity<AuthenticateUserResponse> authenticateUser(@RequestBody @Valid AuthenticateUserRequest request) {
        final AuthenticateUserUseCaseOutput output = this.authenticateUserUseCase.execute(request.toInput());

        return ResponseEntity.ok().body(AuthenticateUserResponse.from(output));
    }
}
