package com.miguel.jobnest.infrastructure.rest.dtos.usercode.requests;

import com.miguel.jobnest.application.usecases.usercode.inputs.SendPasswordResetCodeUseCaseInput;
import jakarta.validation.constraints.NotBlank;

public record SendPasswordResetCodeRequest(
        @NotBlank(message = "Email should not be neither null nor blank")
        String email
) {
    public SendPasswordResetCodeUseCaseInput toInput() {
         return SendPasswordResetCodeUseCaseInput.with(this.email);
    }
}
