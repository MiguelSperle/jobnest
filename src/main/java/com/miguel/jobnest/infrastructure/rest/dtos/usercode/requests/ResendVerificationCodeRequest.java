package com.miguel.jobnest.infrastructure.rest.dtos.usercode.requests;

import com.miguel.jobnest.application.usecases.usercode.inputs.ResendVerificationCodeUseCaseInput;
import jakarta.validation.constraints.NotBlank;

public record ResendVerificationCodeRequest(
        @NotBlank(message = "Email should not be neither null nor blank")
        String email
) {
    public ResendVerificationCodeUseCaseInput toInput() {
        return ResendVerificationCodeUseCaseInput.with(this.email);
    }
}
