package com.miguel.jobnest.infrastructure.rest.dtos.usercode.req;

import com.miguel.jobnest.application.usecases.usercode.resend.verification.ResendVerificationCodeUseCaseInput;
import jakarta.validation.constraints.NotBlank;

public record ResendVerificationCodeRequest(
        @NotBlank(message = "Email should not be neither null nor blank")
        String email
) {
    public ResendVerificationCodeUseCaseInput toInput() {
        return ResendVerificationCodeUseCaseInput.with(this.email);
    }
}
