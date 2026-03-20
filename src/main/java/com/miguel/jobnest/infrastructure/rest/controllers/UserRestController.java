package com.miguel.jobnest.infrastructure.rest.controllers;

import com.miguel.jobnest.application.abstractions.usecases.user.*;
import com.miguel.jobnest.application.usecases.user.inputs.UpdateUserToVerifiedUseCaseInput;
import com.miguel.jobnest.application.usecases.user.outputs.GetAuthenticatedUserUseCaseOutput;
import com.miguel.jobnest.infrastructure.abstractions.rest.controllers.UserControllerAPI;
import com.miguel.jobnest.infrastructure.idempotency.IdempotencyKey;
import com.miguel.jobnest.infrastructure.ratelimiter.CustomRateLimiter;
import com.miguel.jobnest.infrastructure.rest.dtos.MessageResponse;
import com.miguel.jobnest.infrastructure.rest.dtos.user.req.ResetUserPasswordRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.user.req.UpdateUserRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.user.req.UpdateUserPasswordRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.user.res.GetAuthenticatedUserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRestController implements UserControllerAPI {
    private final UpdateUserToVerifiedUseCase updateUserToVerifiedUseCase;
    private final ResetUserPasswordUseCase resetUserPasswordUseCase;
    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final UpdateUserPasswordUseCase updateUserPasswordUseCase;
    private final SoftDeleteUserUseCase softDeleteUserUseCase;

    public UserRestController(
            final UpdateUserToVerifiedUseCase updateUserToVerifiedUseCase,
            final ResetUserPasswordUseCase resetUserPasswordUseCase,
            final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase,
            final UpdateUserUseCase updateUserUseCase,
            final UpdateUserPasswordUseCase updateUserPasswordUseCase,
            final SoftDeleteUserUseCase softDeleteUserUseCase
    ) {
        this.updateUserToVerifiedUseCase = updateUserToVerifiedUseCase;
        this.resetUserPasswordUseCase = resetUserPasswordUseCase;
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.updateUserPasswordUseCase = updateUserPasswordUseCase;
        this.softDeleteUserUseCase = softDeleteUserUseCase;
    }

    @Override
    @IdempotencyKey
    @CustomRateLimiter
    public ResponseEntity<MessageResponse> updateUserToVerified(final String code) {
        this.updateUserToVerifiedUseCase.execute(UpdateUserToVerifiedUseCaseInput.with(code));

        return ResponseEntity.ok().body(MessageResponse.from("User verified successfully"));
    }

    @Override
    @IdempotencyKey
    @CustomRateLimiter
    public ResponseEntity<MessageResponse> resetUserPassword(final String code, final ResetUserPasswordRequest request) {
        this.resetUserPasswordUseCase.execute(request.toInput(code));

        return ResponseEntity.ok().body(MessageResponse.from("User password reset successfully"));
    }

    @Override
    @CustomRateLimiter
    public ResponseEntity<GetAuthenticatedUserResponse> getAuthenticatedUser() {
        final GetAuthenticatedUserUseCaseOutput output = this.getAuthenticatedUserUseCase.execute();

        return ResponseEntity.ok().body(GetAuthenticatedUserResponse.from(output));
    }

    @Override
    @CustomRateLimiter
    public ResponseEntity<MessageResponse> updateUser(final UpdateUserRequest request) {
        this.updateUserUseCase.execute(request.toInput());

        return ResponseEntity.ok().body(MessageResponse.from("User updated successfully"));
    }

    @Override
    @CustomRateLimiter
    public ResponseEntity<MessageResponse> updateUserPassword(final UpdateUserPasswordRequest request) {
        this.updateUserPasswordUseCase.execute(request.toInput());

        return ResponseEntity.ok().body(MessageResponse.from("User password updated successfully"));
    }

    @Override
    @CustomRateLimiter
    public ResponseEntity<MessageResponse> deleteUser() {
        this.softDeleteUserUseCase.execute();

        return ResponseEntity.ok().body(MessageResponse.from("User deleted successfully"));
    }
}
