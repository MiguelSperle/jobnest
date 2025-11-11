package com.miguel.jobnest.application.abstractions.services;

public interface EmailService {
    void sendEmail(String to, String text, String subject);
}
