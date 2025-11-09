package com.miguel.jobnest.application.abstractions.usecases;

public interface UnitUseCase<IN> {
    void execute(IN input);
}
