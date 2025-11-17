package com.miguel.jobnest.application.usecases.usercode.send.password;

public record SendPasswordResetCodeUseCaseInput(
        String email
) {
    public static SendPasswordResetCodeUseCaseInput with(String email) {
        return new SendPasswordResetCodeUseCaseInput(email);
    }
}
