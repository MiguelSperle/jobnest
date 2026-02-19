package com.miguel.jobnest.application.usecases.usercode.inputs;

public record SendPasswordResetCodeUseCaseInput(
        String email
) {
    public static SendPasswordResetCodeUseCaseInput with(final String email) {
        return new SendPasswordResetCodeUseCaseInput(email);
    }
}
