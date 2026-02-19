package com.miguel.jobnest.application.usecases.user.inputs;

public record UpdateUserToVerifiedUseCaseInput(
        String code
) {
    public static UpdateUserToVerifiedUseCaseInput with(final String code) {
        return new UpdateUserToVerifiedUseCaseInput(code);
    }
}
