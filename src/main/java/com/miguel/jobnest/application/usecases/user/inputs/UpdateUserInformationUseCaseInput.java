package com.miguel.jobnest.application.usecases.user.inputs;

public record UpdateUserInformationUseCaseInput(
        String userId,
        String name,
        String email,
        String description,
        String city,
        String state,
        String country
) {
    public static UpdateUserInformationUseCaseInput with(
            String userId,
            String name,
            String email,
            String description,
            String city,
            String state,
            String country
    ) {
        return new UpdateUserInformationUseCaseInput(
                userId,
                name,
                email,
                description,
                city,
                state,
                country
        );
    }
}
