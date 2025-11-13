package com.miguel.jobnest.application.usecases.user.update.status;

public record UpdateUserToVerifiedUseCaseInput(
        String code
) {
    public static UpdateUserToVerifiedUseCaseInput with(String code) {
        return new UpdateUserToVerifiedUseCaseInput(code);
    }
}
