package com.miguel.jobnest.infrastructure.exceptions;

public class EmailSendFailedException extends RuntimeException {
    public EmailSendFailedException(String message) {
        super(message);
    }

    public static EmailSendFailedException with(String message) {
        return new EmailSendFailedException(message);
    }
}
