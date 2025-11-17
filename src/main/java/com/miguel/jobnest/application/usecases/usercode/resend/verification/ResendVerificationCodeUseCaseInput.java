package com.miguel.jobnest.application.usecases.usercode.resend.verification;

public record ResendVerificationCodeUseCaseInput(
        String email
) {
    public static ResendVerificationCodeUseCaseInput with(String email) {
        return new ResendVerificationCodeUseCaseInput(email);
    }
}
