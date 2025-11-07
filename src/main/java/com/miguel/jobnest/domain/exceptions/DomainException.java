package com.miguel.jobnest.domain.exceptions;

public class DomainException extends RuntimeException {
    private final int statusCode;

    public DomainException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public static DomainException with(String message, int statusCode) {
        return new DomainException(message, statusCode);
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}
