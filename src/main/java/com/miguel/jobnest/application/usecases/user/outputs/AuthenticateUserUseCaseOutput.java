package com.miguel.jobnest.application.usecases.user.outputs;

public record AuthenticateUserUseCaseOutput(
        String accessToken
) {
    public static AuthenticateUserUseCaseOutput from(final String accessToken) {
        return new AuthenticateUserUseCaseOutput(accessToken);
    }
}
