package com.miguel.jobnest.infrastructure.rest.dtos.usercode.req;

import com.miguel.jobnest.application.usecases.usercode.resend.ResendVerificationCodeUseCaseInput;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ResendVerificationCodeRequest(
        @NotBlank(message = "Email should not be neither null nor blank")
        @Email(message = "Email should be valid")
        String email
) {
    public ResendVerificationCodeUseCaseInput toInput() {
        return ResendVerificationCodeUseCaseInput.with(
                this.email
        );
    }
}
