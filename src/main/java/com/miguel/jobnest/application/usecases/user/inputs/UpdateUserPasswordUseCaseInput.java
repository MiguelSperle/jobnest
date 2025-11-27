package com.miguel.jobnest.application.usecases.user.inputs;

public record UpdateUserPasswordUseCaseInput(
        String id,
        String currentPassword,
        String password
) {
    public static UpdateUserPasswordUseCaseInput with(
            String id,
            String currentPassword,
            String password
    ) {
        return new UpdateUserPasswordUseCaseInput(
                id,
                currentPassword,
                password
        );
    }
}
