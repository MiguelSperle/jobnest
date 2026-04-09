package com.miguel.jobnest.infrastructure.rest.controllers;

import com.miguel.jobnest.application.abstractions.usecases.usercode.SendPasswordResetCodeUseCase;
import com.miguel.jobnest.application.abstractions.usecases.usercode.ResendVerificationCodeUseCase;
import com.miguel.jobnest.application.abstractions.usecases.usercode.ValidatePasswordResetCodeUseCase;
import com.miguel.jobnest.application.usecases.usercode.inputs.ValidatePasswordResetCodeUseCaseInput;
import com.miguel.jobnest.infrastructure.abstractions.rest.controllers.UserCodeControllerAPI;
import com.miguel.jobnest.infrastructure.idempotency.IdempotencyKey;
import com.miguel.jobnest.infrastructure.rest.dtos.usercode.req.SendPasswordResetCodeRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.usercode.req.ResendVerificationCodeRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserCodeRestController implements UserCodeControllerAPI {
    private final ResendVerificationCodeUseCase resendVerificationCodeUseCase;
    private final SendPasswordResetCodeUseCase sendPasswordResetCodeUseCase;
    private final ValidatePasswordResetCodeUseCase validatePasswordResetCodeUseCase;

    public UserCodeRestController(
            final ResendVerificationCodeUseCase resendVerificationCodeUseCase,
            final SendPasswordResetCodeUseCase sendPasswordResetCodeUseCase,
            final ValidatePasswordResetCodeUseCase validatePasswordResetCodeUseCase
    ) {
        this.resendVerificationCodeUseCase = resendVerificationCodeUseCase;
        this.sendPasswordResetCodeUseCase = sendPasswordResetCodeUseCase;
        this.validatePasswordResetCodeUseCase = validatePasswordResetCodeUseCase;
    }

    @Override
    @IdempotencyKey
    public ResponseEntity<MessageResponse> resendVerificationCode(final ResendVerificationCodeRequest request) {
        this.resendVerificationCodeUseCase.execute(request.toInput());

        return ResponseEntity.ok().body(MessageResponse.from("Verification code sent again successfully"));
    }

    @Override
    @IdempotencyKey
    public ResponseEntity<MessageResponse> sendPasswordResetCode(final SendPasswordResetCodeRequest request) {
        this.sendPasswordResetCodeUseCase.execute(request.toInput());

        return ResponseEntity.ok().body(MessageResponse.from("Password reset code sent successfully"));
    }

    @Override
    public ResponseEntity<Void> validatePasswordResetCode(final String code) {
        this.validatePasswordResetCodeUseCase.execute(ValidatePasswordResetCodeUseCaseInput.with(code));

        return ResponseEntity.noContent().build();
    }
}
