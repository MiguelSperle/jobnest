package com.miguel.jobnest.application.usecases.user.inputs;

public record UpdateUserPasswordUseCaseInput(
        String userId,
        String currentPassword,
        String password
) {
    public static UpdateUserPasswordUseCaseInput with(
            final String userId,
            final String currentPassword,
            final String password
    ) {
        return new UpdateUserPasswordUseCaseInput(
                userId,
                currentPassword,
                password
        );
    }
}
