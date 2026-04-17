package com.miguel.jobnest.application.abstractions.services;

public interface JwtGeneratorService {
    String generateJwt(String userId, String role);
}
