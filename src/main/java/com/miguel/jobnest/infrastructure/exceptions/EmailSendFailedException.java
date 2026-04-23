package com.miguel.jobnest.infrastructure.exceptions;

public class EmailSendFailedException extends RuntimeException {
    public EmailSendFailedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public static EmailSendFailedException with(final String message, final Throwable cause) {
        return new EmailSendFailedException(message, cause);
    }
}
