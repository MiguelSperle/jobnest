package com.miguel.jobnest.infrastructure.rest.dtos.user.req;

import com.miguel.jobnest.application.usecases.user.reset.password.ResetUserPasswordUseCaseInput;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ResetUserPasswordRequest(
        @NotNull(message = "Password should not be null")
        @Size(min = 5, max = 100, message = "Password should contain between 5 and 100 characters")
        String password
) {
    public ResetUserPasswordUseCaseInput toInput(String code) {
        return ResetUserPasswordUseCaseInput.with(code, this.password);
    }
}
