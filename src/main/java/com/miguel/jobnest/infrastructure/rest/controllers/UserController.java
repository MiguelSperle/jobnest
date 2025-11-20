package com.miguel.jobnest.infrastructure.rest.controllers;

import com.miguel.jobnest.application.abstractions.usecases.user.GetAuthenticatedUserUseCase;
import com.miguel.jobnest.application.abstractions.usecases.user.ResetUserPasswordUseCase;
import com.miguel.jobnest.application.abstractions.usecases.user.UpdateUserToVerifiedUseCase;
import com.miguel.jobnest.application.usecases.user.inputs.UpdateUserToVerifiedUseCaseInput;
import com.miguel.jobnest.application.usecases.user.outputs.GetAuthenticatedUserUseCaseOutput;
import com.miguel.jobnest.infrastructure.rest.dtos.common.res.MessageResponse;
import com.miguel.jobnest.infrastructure.rest.dtos.user.req.ResetUserPasswordRequest;
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

    @PatchMapping("/verification/{code}")
    @RateLimiter(name = "rateLimitConfiguration")
    public ResponseEntity<MessageResponse> updateUserToVerified(@PathVariable String code) {
        this.updateUserToVerifiedUseCase.execute(UpdateUserToVerifiedUseCaseInput.with(code));

        return ResponseEntity.ok().body(MessageResponse.from("User verified successfully"));
    }

    @PatchMapping("/password-reset/{code}")
    @RateLimiter(name = "rateLimitConfiguration")
    public ResponseEntity<MessageResponse> resetUserPassword(
            @PathVariable String code,
            @RequestBody @Valid ResetUserPasswordRequest resetUserPasswordRequest
    ) {
        this.resetUserPasswordUseCase.execute(resetUserPasswordRequest.toInput(code));

        return ResponseEntity.ok().body(MessageResponse.from("User password reset successfully"));
    }

    @GetMapping("/me")
    @RateLimiter(name = "rateLimitConfiguration")
    public ResponseEntity<GetAuthenticatedUserResponse> getAuthenticatedUser() {
        final GetAuthenticatedUserUseCaseOutput output = this.getAuthenticatedUserUseCase.execute();

        return ResponseEntity.ok().body(GetAuthenticatedUserResponse.from(output));
    }
}
