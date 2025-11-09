package com.miguel.jobnest.application.abstractions.usecases;

public interface UseCase<IN, OUT> {
    OUT execute(IN input);
}
