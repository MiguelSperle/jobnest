package com.miguel.jobnest.application.usecases.user.inputs;

public record DeleteUserUseCaseInput(
        String id
) {
    public static DeleteUserUseCaseInput with(String id) {
        return new DeleteUserUseCaseInput(id);
    }
}
