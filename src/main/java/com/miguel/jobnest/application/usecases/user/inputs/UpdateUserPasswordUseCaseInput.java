package com.miguel.jobnest.application.usecases.user.inputs;

public record UpdateUserPasswordUseCaseInput(
        String userId,
        String currentPassword,
        String password
) {
    public static UpdateUserPasswordUseCaseInput with(
            String userId,
            String currentPassword,
            String password
    ) {
        return new UpdateUserPasswordUseCaseInput(
                userId,
                currentPassword,
                password
        );
    }
}
