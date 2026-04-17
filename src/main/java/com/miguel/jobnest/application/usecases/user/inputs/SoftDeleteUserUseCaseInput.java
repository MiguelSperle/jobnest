package com.miguel.jobnest.application.usecases.user.inputs;

public record SoftDeleteUserUseCaseInput(
        String userId
) {
    public static SoftDeleteUserUseCaseInput with(final String userId) {
        return new SoftDeleteUserUseCaseInput(userId);
    }
}
