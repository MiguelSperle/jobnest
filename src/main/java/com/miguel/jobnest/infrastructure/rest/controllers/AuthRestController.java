package com.miguel.jobnest.infrastructure.rest.controllers;

import com.miguel.jobnest.application.abstractions.usecases.user.AuthenticateUserUseCase;
import com.miguel.jobnest.application.abstractions.usecases.user.CreateUserUseCase;
import com.miguel.jobnest.application.usecases.user.outputs.AuthenticateUserUseCaseOutput;
import com.miguel.jobnest.infrastructure.abstractions.rest.controllers.AuthControllerAPI;
import com.miguel.jobnest.infrastructure.idempotency.IdempotencyKey;
import com.miguel.jobnest.infrastructure.ratelimiter.CustomRateLimiter;
import com.miguel.jobnest.infrastructure.rest.dtos.user.req.AuthenticateUserRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.user.req.CreateUserRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.MessageResponse;
import com.miguel.jobnest.infrastructure.rest.dtos.user.res.AuthenticateUserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthRestController implements AuthControllerAPI {
    private final CreateUserUseCase createUserUseCase;
    private final AuthenticateUserUseCase authenticateUserUseCase;

    public AuthRestController(
            final CreateUserUseCase createUserUseCase,
            final AuthenticateUserUseCase authenticateUserUseCase
    ) {
        this.createUserUseCase = createUserUseCase;
        this.authenticateUserUseCase = authenticateUserUseCase;
    }

    @Override
    @IdempotencyKey
    @CustomRateLimiter
    public ResponseEntity<MessageResponse> createUser(final CreateUserRequest request) {
        this.createUserUseCase.execute(request.toInput());

        return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.from("User created successfully"));
    }

    @Override
    @CustomRateLimiter
    public ResponseEntity<AuthenticateUserResponse> authenticateUser(final AuthenticateUserRequest request) {
        final AuthenticateUserUseCaseOutput output = this.authenticateUserUseCase.execute(request.toInput());

        return ResponseEntity.ok().body(AuthenticateUserResponse.from(output));
    }
}
