package com.miguel.jobnest.infrastructure.exceptions;

public class EventPublishingFailedException extends RuntimeException {
    public EventPublishingFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public static EventPublishingFailedException with(String message, Throwable cause) {
        return new EventPublishingFailedException(message, cause);
    }
}
