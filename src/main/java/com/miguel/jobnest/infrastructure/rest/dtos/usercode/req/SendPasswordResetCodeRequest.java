package com.miguel.jobnest.infrastructure.rest.dtos.usercode.req;

import com.miguel.jobnest.application.usecases.usercode.send.password.SendPasswordResetCodeUseCaseInput;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SendPasswordResetCodeRequest(
        @NotBlank(message = "Email should not be neither null nor blank")
        @Email(message = "Email should be valid")
        String email
) {
    public SendPasswordResetCodeUseCaseInput toInput() {
         return SendPasswordResetCodeUseCaseInput.with(
                 this.email
         );
    }
}
