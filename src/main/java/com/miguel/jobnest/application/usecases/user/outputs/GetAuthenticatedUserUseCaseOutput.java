package com.miguel.jobnest.application.usecases.user.outputs;

import com.miguel.jobnest.domain.entities.User;

public record GetAuthenticatedUserUseCaseOutput(
        User user
) {
    public static GetAuthenticatedUserUseCaseOutput from(final User user) {
        return new GetAuthenticatedUserUseCaseOutput(user);
    }
}
