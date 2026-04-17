package com.miguel.jobnest.application.usecases.user.inputs;

public record GetAuthenticatedUserUseCaseInput(
        String userId
) {
    public static GetAuthenticatedUserUseCaseInput with(final String userId) {
        return new GetAuthenticatedUserUseCaseInput(userId);
    }
}
