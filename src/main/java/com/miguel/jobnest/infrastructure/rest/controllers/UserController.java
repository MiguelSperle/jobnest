package com.miguel.jobnest.infrastructure.rest.controllers;

import com.miguel.jobnest.application.abstractions.usecases.user.*;
import com.miguel.jobnest.application.usecases.user.inputs.UpdateUserInformationUseCaseInput;
import com.miguel.jobnest.application.usecases.user.inputs.UpdateUserToVerifiedUseCaseInput;
import com.miguel.jobnest.application.usecases.user.outputs.GetAuthenticatedUserUseCaseOutput;
import com.miguel.jobnest.infrastructure.rest.dtos.common.res.MessageResponse;
import com.miguel.jobnest.infrastructure.rest.dtos.user.req.ResetUserPasswordRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.user.req.UpdateUserInformationRequest;
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
    private final UpdateUserInformationUseCase updateUserInformationUseCase;
    private final UpdateUserPasswordUseCase updateUserPasswordUseCase;

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

    @PatchMapping("/{id}/information")
    @RateLimiter(name = "rateLimitConfiguration")
    public ResponseEntity<MessageResponse> updateUserInformation(
            @PathVariable String id,
            @RequestBody @Valid UpdateUserInformationRequest request
    ) {
        this.updateUserInformationUseCase.execute(request.toInput(id));

        return ResponseEntity.ok().body(MessageResponse.from("User information updated successfully"));
    }

    @PatchMapping("/{id}/password")
    @RateLimiter(name = "rateLimitConfiguration")
    public ResponseEntity<MessageResponse> updateUserPassword(
            @PathVariable String id,
            @RequestBody @Valid UpdateUserPasswordRequest request
    ) {
        this.updateUserPasswordUseCase.execute(request.toInput(id));

        return ResponseEntity.ok().body(MessageResponse.from("User password updated successfully"));
    }
}
