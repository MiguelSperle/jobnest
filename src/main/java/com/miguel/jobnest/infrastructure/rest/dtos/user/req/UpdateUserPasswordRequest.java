package com.miguel.jobnest.infrastructure.rest.dtos.user.req;

import com.miguel.jobnest.application.usecases.user.inputs.UpdateUserPasswordUseCaseInput;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateUserPasswordRequest(
        @NotBlank(message = "Current password should not neither null nor blank")
        String currentPassword,

        @NotNull(message = "Password should not be null")
        @Size(min = 5, max = 100, message = "Password should contain between 5 and 100 characters")
        String password
) {
    public UpdateUserPasswordUseCaseInput toInput() {
        return UpdateUserPasswordUseCaseInput.with(
                this.currentPassword,
                this.password
        );
    }
}
