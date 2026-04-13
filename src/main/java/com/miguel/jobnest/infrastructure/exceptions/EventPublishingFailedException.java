package com.miguel.jobnest.infrastructure.exceptions;

public class EventPublishingFailedException extends RuntimeException {
    public EventPublishingFailedException(String message) {
        super(message);
    }

    public static EventPublishingFailedException with(String message) {
        return new EventPublishingFailedException(message);
    }
}
