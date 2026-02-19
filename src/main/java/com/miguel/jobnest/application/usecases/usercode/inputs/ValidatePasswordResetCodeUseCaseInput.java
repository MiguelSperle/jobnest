package com.miguel.jobnest.application.usecases.usercode.inputs;

public record ValidatePasswordResetCodeUseCaseInput(
        String code
) {
    public static ValidatePasswordResetCodeUseCaseInput with(final String code) {
        return new ValidatePasswordResetCodeUseCaseInput(code);
    }
}
