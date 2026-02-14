package com.miguel.jobnest.infrastructure.abstractions.services;

public interface EmailService {
    void sendEmail(String to, String text, String subject);
}
