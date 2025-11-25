package com.miguel.jobnest.infrastructure.rest.dtos.user.req;

import com.miguel.jobnest.application.usecases.user.inputs.RegisterUserUseCaseInput;
import com.miguel.jobnest.domain.enums.AuthorizationRole;
import com.miguel.jobnest.infrastructure.annotations.EnumCheck;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterUserRequest(
        @NotBlank(message = "Name should not be neither null nor blank")
        @Size(max = 100, message = "Name should not exceed 100 characters")
        String name,

        @NotBlank(message = "Email should not be neither null nor blank")
        @Size(max = 255, message = "Email should not exceed 255 characters")
        @Email(message = "Email should be valid")
        String email,

        @NotNull(message = "Password should not be null")
        @Size(min = 5, max = 100, message = "Password should contain between 5 and 100 characters")
        String password,

        @EnumCheck(enumClass = AuthorizationRole.class, message = "Authorization role should be either CANDIDATE or RECRUITER")
        String authorizationRole,

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
    public RegisterUserUseCaseInput toInput() {
        return RegisterUserUseCaseInput.with(
                this.name,
                this.email,
                this.password,
                this.authorizationRole,
                this.city,
                this.state,
                this.country
        );
    }
}
