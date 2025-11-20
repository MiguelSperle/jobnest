package com.miguel.jobnest.infrastructure.rest.dtos.user.req;

import com.miguel.jobnest.application.usecases.user.inputs.AuthenticateUserUseCaseInput;
import jakarta.validation.constraints.NotBlank;

public record AuthenticateUserRequest(
        @NotBlank(message = "Email should not be neither null nor blank")
        String email,

        @NotBlank(message = "Password should not be neither null nor blank")
        String password
) {
    public AuthenticateUserUseCaseInput toInput() {
        return AuthenticateUserUseCaseInput.with(this.email, this.password);
    }
}
