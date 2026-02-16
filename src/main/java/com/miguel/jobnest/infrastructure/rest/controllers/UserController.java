package com.miguel.jobnest.infrastructure.rest.controllers;

import com.miguel.jobnest.application.abstractions.usecases.user.*;
import com.miguel.jobnest.application.usecases.user.inputs.UpdateUserToVerifiedUseCaseInput;
import com.miguel.jobnest.application.usecases.user.outputs.GetAuthenticatedUserUseCaseOutput;
import com.miguel.jobnest.infrastructure.idempotency.Idempotency;
import com.miguel.jobnest.infrastructure.rest.dtos.MessageResponse;
import com.miguel.jobnest.infrastructure.rest.dtos.user.req.ResetUserPasswordRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.user.req.UpdateUserRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.user.req.UpdateUserPasswordRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.user.res.GetAuthenticatedUserResponse;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UpdateUserToVerifiedUseCase updateUserToVerifiedUseCase;
    private final ResetUserPasswordUseCase resetUserPasswordUseCase;
    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final UpdateUserPasswordUseCase updateUserPasswordUseCase;
    private final SoftDeleteUserUseCase softDeleteUserUseCase;

    @PatchMapping("/verification/{code}")
    @RateLimiter(name = "rateLimitConfiguration")
    @Idempotency
    public ResponseEntity<MessageResponse> updateUserToVerified(@PathVariable String code) {
        this.updateUserToVerifiedUseCase.execute(UpdateUserToVerifiedUseCaseInput.with(code));

        return ResponseEntity.ok().body(MessageResponse.from("User verified successfully"));
    }

    @PatchMapping("/password-reset/{code}")
    @RateLimiter(name = "rateLimitConfiguration")
    @Idempotency
    public ResponseEntity<MessageResponse> resetUserPassword(
            @PathVariable String code,
            @RequestBody @Valid ResetUserPasswordRequest request
    ) {
        this.resetUserPasswordUseCase.execute(request.toInput(code));

        return ResponseEntity.ok().body(MessageResponse.from("User password reset successfully"));
    }

    @GetMapping("/me")
    @RateLimiter(name = "rateLimitConfiguration")
    public ResponseEntity<GetAuthenticatedUserResponse> getAuthenticatedUser() {
        final GetAuthenticatedUserUseCaseOutput output = this.getAuthenticatedUserUseCase.execute();

        return ResponseEntity.ok().body(GetAuthenticatedUserResponse.from(output));
    }

    @PatchMapping("/update/information")
    @RateLimiter(name = "rateLimitConfiguration")
    public ResponseEntity<MessageResponse> updateUser(@RequestBody @Valid UpdateUserRequest request) {
        this.updateUserUseCase.execute(request.toInput());

        return ResponseEntity.ok().body(MessageResponse.from("User updated successfully"));
    }

    @PatchMapping("/update/password")
    @RateLimiter(name = "rateLimitConfiguration")
    public ResponseEntity<MessageResponse> updateUserPassword(@RequestBody @Valid UpdateUserPasswordRequest request) {
        this.updateUserPasswordUseCase.execute(request.toInput());

        return ResponseEntity.ok().body(MessageResponse.from("User password updated successfully"));
    }

    @DeleteMapping("/delete")
    @RateLimiter(name = "rateLimitConfiguration")
    public ResponseEntity<MessageResponse> deleteUser() {
        this.softDeleteUserUseCase.execute();

        return ResponseEntity.ok().body(MessageResponse.from("User deleted successfully"));
    }
}
