package com.miguel.jobnest.infrastructure.abstractions.services;

import com.auth0.jwt.interfaces.DecodedJWT;

public interface JwtTokenDecoderService {
    DecodedJWT decodeJwt(String jwt);
}
