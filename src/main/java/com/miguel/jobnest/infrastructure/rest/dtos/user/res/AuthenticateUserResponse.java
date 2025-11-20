package com.miguel.jobnest.infrastructure.rest.dtos.user.res;

import com.miguel.jobnest.application.usecases.user.outputs.AuthenticateUserUseCaseOutput;

public record AuthenticateUserResponse(
        String accessToken
) {
    public static AuthenticateUserResponse from(AuthenticateUserUseCaseOutput output) {
        return new AuthenticateUserResponse(output.accessToken());
    }
}
