package com.miguel.jobnest.application.abstractions.services;

public interface JwtTokenGeneratorService {
    String generateJwt(String userId, String role);
}
