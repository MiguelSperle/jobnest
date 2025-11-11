package com.miguel.jobnest.application.abstractions.providers;

public interface CodeProvider {
    String generateCode(int codeLength, String characters);
}
