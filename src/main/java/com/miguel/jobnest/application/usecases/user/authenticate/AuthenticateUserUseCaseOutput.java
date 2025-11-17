package com.miguel.jobnest.application.usecases.user.authenticate;

public record AuthenticateUserUseCaseOutput(
        String accessToken
) {
    public static AuthenticateUserUseCaseOutput from(String accessToken) {
        return new AuthenticateUserUseCaseOutput(accessToken);
    }
}
