package com.miguel.jobnest.application.abstractions.services;

import com.miguel.jobnest.domain.jwt.DecodedJwtToken;

public interface JwtService {
    String generateJwt(String userId, String role);
    DecodedJwtToken validateJwt(String jwt);
}
