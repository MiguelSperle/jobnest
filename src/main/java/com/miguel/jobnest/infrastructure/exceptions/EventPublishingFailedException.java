package com.miguel.jobnest.infrastructure.exceptions;

public class EventPublishingFailedException extends RuntimeException {
    public EventPublishingFailedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public static EventPublishingFailedException with(final String message, final Throwable cause) {
        return new EventPublishingFailedException(message, cause);
    }
}
