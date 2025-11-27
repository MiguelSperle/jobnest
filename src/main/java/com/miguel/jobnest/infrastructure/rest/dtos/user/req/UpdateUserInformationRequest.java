package com.miguel.jobnest.infrastructure.rest.dtos.user.req;

import com.miguel.jobnest.application.usecases.user.inputs.UpdateUserUseCaseInput;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserInformationRequest(
        @NotBlank(message = "Name should not be neither null nor blank")
        @Size(max = 100, message = "Name should not exceed 100 characters")
        String name,

        @NotBlank(message = "Email should not be neither null nor blank")
        @Size(max = 255, message = "Email should not exceed 255 characters")
        @Email(message = "Email should be valid")
        String email,

        @Size(max = 1000, message = "Description should not exceed 1000 characters")
        String description,

        @NotBlank(message = "City should not be neither null nor blank")
        @Size(max = 50, message = "City should not exceed 50 characters")
        String city,

        @NotBlank(message = "State should not be neither null nor blank")
        @Size(max = 50, message = "State should not exceed 50 characters")
        String state,

        @NotBlank(message = "Country should not be neither null nor blank")
        @Size(max = 50, message = "Country should not exceed 50 characters")
        String country
) {
    public UpdateUserUseCaseInput toInput(String id) {
        return UpdateUserUseCaseInput.with(
                id,
                this.name,
                this.email,
                this.description,
                this.city,
                this.state,
                this.country
        );
    }
}
