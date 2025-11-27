package com.miguel.jobnest.application.usecases.user.inputs;

public record UpdateUserUseCaseInput(
        String id,
        String name,
        String email,
        String description,
        String city,
        String state,
        String country
) {
    public static UpdateUserUseCaseInput with(
            String id,
            String name,
            String email,
            String description,
            String city,
            String state,
            String country
    ) {
        return new UpdateUserUseCaseInput(
                id,
                name,
                email,
                description,
                city,
                state,
                country
        );
    }
}
