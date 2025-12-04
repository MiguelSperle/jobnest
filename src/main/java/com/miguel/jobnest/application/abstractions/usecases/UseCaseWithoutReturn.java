package com.miguel.jobnest.application.abstractions.usecases;

public interface UseCaseWithoutReturn<IN> {
    void execute(IN input);
}
