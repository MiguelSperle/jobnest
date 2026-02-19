package com.miguel.jobnest.application.usecases.user.inputs;

public record AuthenticateUserUseCaseInput(
        String email,
        String password
) {
    public static AuthenticateUserUseCaseInput with(final String email, final String password) {
        return new AuthenticateUserUseCaseInput(email, password);
    }
}
