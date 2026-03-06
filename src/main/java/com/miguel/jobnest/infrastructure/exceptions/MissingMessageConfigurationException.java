package com.miguel.jobnest.infrastructure.exceptions;

public class MissingMessageConfigurationException extends RuntimeException {
    public MissingMessageConfigurationException(String message) {
        super(message);
    }

    public static MissingMessageConfigurationException with(String message) {
        return new MissingMessageConfigurationException(message);
    }
}
