package com.miguel.jobnest.infrastructure.exceptions;

public class EmailSendFailedException extends RuntimeException {
    public EmailSendFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public static EmailSendFailedException with(String message, Throwable cause) {
        return new EmailSendFailedException(message, cause);
    }
}
