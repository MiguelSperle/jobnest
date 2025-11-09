package com.miguel.jobnest.application.usecases.user.inputs;

import com.miguel.jobnest.domain.enums.AuthorizationRole;

public record CreateUserUseCaseInput(
        String name,
        String email,
        String password,
        String authorizationRole,
        String city,
        String state,
        String country
) {
    public static CreateUserUseCaseInput with(
            String name,
            String email,
            String password,
            String authorizationRole,
            String city,
            String state,
            String country
    ) {
        return new CreateUserUseCaseInput(
                name,
                email,
                password,
                authorizationRole,
                city,
                state,
                country
        );
    }
}
