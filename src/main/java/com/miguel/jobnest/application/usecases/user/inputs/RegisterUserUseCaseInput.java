package com.miguel.jobnest.application.usecases.user.inputs;

public record RegisterUserUseCaseInput(
        String name,
        String email,
        String password,
        String authorizationRole,
        String city,
        String state,
        String country
) {
    public static RegisterUserUseCaseInput with(
            String name,
            String email,
            String password,
            String authorizationRole,
            String city,
            String state,
            String country
    ) {
        return new RegisterUserUseCaseInput(
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
