package com.miguel.jobnest.application.usecases.user.inputs;

public record ResetUserPasswordUseCaseInput(
        String code,
        String password
) {
    public static ResetUserPasswordUseCaseInput with(final String code, final String password) {
        return new ResetUserPasswordUseCaseInput(code, password);
    }
}
