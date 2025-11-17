package com.miguel.jobnest.application.usecases.usercode.verification.resend;

public record ResendVerificationCodeUseCaseInput(
        String email
) {
    public static ResendVerificationCodeUseCaseInput with(String email) {
        return new ResendVerificationCodeUseCaseInput(email);
    }
}
