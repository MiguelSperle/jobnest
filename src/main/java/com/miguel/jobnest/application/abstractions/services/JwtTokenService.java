package com.miguel.jobnest.application.abstractions.services;

import java.util.List;

public interface JwtTokenService {
    String generateAccessToken(String userId, String role, List<String> permissions);
}
