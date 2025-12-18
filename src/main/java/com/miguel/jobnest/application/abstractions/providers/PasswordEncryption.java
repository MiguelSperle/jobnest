package com.miguel.jobnest.application.abstractions.providers;

public interface PasswordEncryption {
    String encode(String password);
    boolean matches(String password, String encodedPassword);
}
