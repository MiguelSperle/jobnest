package com.miguel.jobnest.domain.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(final String message) {
        super(message);
    }

    public static NotFoundException with(final String message) {
        return new NotFoundException(message);
    }
}
