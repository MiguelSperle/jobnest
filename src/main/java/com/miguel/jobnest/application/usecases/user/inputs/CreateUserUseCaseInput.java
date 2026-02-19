package com.miguel.jobnest.application.usecases.user.inputs;

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
            final String name,
            final String email,
            final String password,
            final String authorizationRole,
            final String city,
            final String state,
            final String country
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
