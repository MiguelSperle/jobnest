package com.miguel.jobnest.infrastructure.rest.dtos.user.res;

import com.miguel.jobnest.application.usecases.user.outputs.GetAuthenticatedUserUseCaseOutput;
import com.miguel.jobnest.domain.enums.AuthorizationRole;

public record GetAuthenticatedUserResponse(
        String id,
        String name,
        String email,
        String description,
        AuthorizationRole authorizationRole,
        String city,
        String state,
        String country
) {
    public static GetAuthenticatedUserResponse from(GetAuthenticatedUserUseCaseOutput output) {
        return new GetAuthenticatedUserResponse(
                output.user().getId(),
                output.user().getName(),
                output.user().getEmail(),
                output.user().getDescription(),
                output.user().getAuthorizationRole(),
                output.user().getCity(),
                output.user().getState(),
                output.user().getCountry()
        );
    }
}
