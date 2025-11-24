package com.miguel.jobnest.application.usecases.user.inputs;

public record DeleteUserUseCaseInput(
        String userId
) {
    public static DeleteUserUseCaseInput with(String userId) {
        return new DeleteUserUseCaseInput(userId);
    }
}
