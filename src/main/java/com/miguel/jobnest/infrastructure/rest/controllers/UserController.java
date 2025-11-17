package com.miguel.jobnest.infrastructure.rest.controllers;

import com.miguel.jobnest.application.abstractions.usecases.user.ResetUserPasswordUseCase;
import com.miguel.jobnest.application.abstractions.usecases.user.UpdateUserToVerifiedUseCase;
import com.miguel.jobnest.application.usecases.user.update.status.UpdateUserToVerifiedUseCaseInput;
import com.miguel.jobnest.infrastructure.rest.dtos.common.res.MessageResponse;
import com.miguel.jobnest.infrastructure.rest.dtos.user.req.ResetUserPasswordRequest;
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
}
