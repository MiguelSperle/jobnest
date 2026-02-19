package com.miguel.jobnest.application.usecases.usercode.inputs;

public record ResendVerificationCodeUseCaseInput(
        String email
) {
    public static ResendVerificationCodeUseCaseInput with(final String email) {
        return new ResendVerificationCodeUseCaseInput(email);
    }
}
