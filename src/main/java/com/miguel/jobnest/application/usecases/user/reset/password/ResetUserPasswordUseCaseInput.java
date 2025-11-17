package com.miguel.jobnest.application.usecases.user.reset.password;

public record ResetUserPasswordUseCaseInput(
        String code,
        String password
) {
    public static ResetUserPasswordUseCaseInput with(String code, String password) {
        return new ResetUserPasswordUseCaseInput(code, password);
    }
}
