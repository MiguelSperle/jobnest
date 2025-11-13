package com.miguel.jobnest.application.abstractions.providers;

public interface PasswordEncryptionProvider {
    String encode(String password);
    boolean matches(String password, String encodedPassword);
}
