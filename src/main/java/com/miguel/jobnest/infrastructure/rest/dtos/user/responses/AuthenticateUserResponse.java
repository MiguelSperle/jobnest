package com.miguel.jobnest.infrastructure.rest.dtos.user.responses;

import com.miguel.jobnest.application.usecases.user.outputs.AuthenticateUserUseCaseOutput;

public record AuthenticateUserResponse(
        String accessToken
) {
    public static AuthenticateUserResponse from(AuthenticateUserUseCaseOutput authenticateUserUseCaseOutput) {
        return new AuthenticateUserResponse(authenticateUserUseCaseOutput.accessToken());
    }
}
