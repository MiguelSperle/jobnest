package com.miguel.jobnest.application.usecases.user.inputs;

public record UpdateUserUseCaseInput(
        String name,
        String email,
        String description,
        String city,
        String state,
        String country
) {
    public static UpdateUserUseCaseInput with(
            final String name,
            final String email,
            final String description,
            final String city,
            final String state,
            final String country
    ) {
        return new UpdateUserUseCaseInput(
                name,
                email,
                description,
                city,
                state,
                country
        );
    }
}
