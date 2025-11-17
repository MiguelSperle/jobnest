package com.miguel.jobnest.infrastructure.rest.dtos.user.res;

import com.miguel.jobnest.application.usecases.user.authenticate.AuthenticateUserUseCaseOutput;

public record AuthenticateUserResponse(
        String accessToken
) {
    public static AuthenticateUserResponse from(AuthenticateUserUseCaseOutput authenticateUserUseCaseOutput) {
        return new AuthenticateUserResponse(authenticateUserUseCaseOutput.accessToken());
    }
}
