package com.miguel.jobnest.infrastructure.exceptions;

public class TooManyRequestsException extends RuntimeException {
    public TooManyRequestsException(String message) {
        super(message);
    }

    public static TooManyRequestsException with(String message) {
        return new TooManyRequestsException(message);
    }
}
