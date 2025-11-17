package com.miguel.jobnest.application.usecases.usercode.password.validate;

public record ValidatePasswordResetCodeUseCaseInput(
        String code
) {
    public static ValidatePasswordResetCodeUseCaseInput with(String code) {
        return new ValidatePasswordResetCodeUseCaseInput(code);
    }
}
