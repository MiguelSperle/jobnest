package com.miguel.jobnest.application.usecases.user.inputs;

public record UpdateUserPasswordUseCaseInput(
        String currentPassword,
        String password
) {
    public static UpdateUserPasswordUseCaseInput with(
            final String currentPassword,
            final String password
    ) {
        return new UpdateUserPasswordUseCaseInput(
                currentPassword,
                password
        );
    }
}
