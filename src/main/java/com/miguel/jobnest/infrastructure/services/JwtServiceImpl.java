package com.miguel.jobnest.infrastructure.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.miguel.jobnest.application.abstractions.services.JwtService;
import com.miguel.jobnest.domain.jwt.DecodedJwtToken;
import com.miguel.jobnest.infrastructure.services.exceptions.JwtTokenCreationFailedException;
import com.miguel.jobnest.infrastructure.services.exceptions.JwtTokenValidationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${spring.api.security.token.secret}")
    private String secret;

    private static final Logger log = LoggerFactory.getLogger(JwtServiceImpl.class);

    @Override
    public String generateJwt(String userId, String role) {
        try {
            final Algorithm algorithm = Algorithm.HMAC256(this.secret);

            return JWT.create()
                    .withIssuer("jobnest")
                    .withSubject(userId)
                    .withClaim("role", role)
                    .withExpiresAt(this.genExpirationDate(Instant.now()))
                    .sign(algorithm);
        } catch (Exception ex) {
            log.error("Failed to create JWT token for userId: [{}] with role: [{}]", userId, role, ex);
            throw JwtTokenCreationFailedException.with("Failed to create JWT token");
        }
    }

    @Override
    public DecodedJwtToken validateJwt(String jwt) {
        try {
            final Algorithm algorithm = Algorithm.HMAC256(this.secret);

            final DecodedJWT decodedJWT = JWT.require(algorithm).withIssuer("jobnest").build().verify(jwt);

            return new DecodedJwtToken(decodedJWT.getSubject(), decodedJWT.getClaim("role").asString());
        } catch (Exception ex) {
            log.error("Failed to validate JWT token", ex);
            throw JwtTokenValidationFailedException.with("Invalid JWT token");
        }
    }

    private Instant genExpirationDate(Instant now) {
        return now.plus(5, ChronoUnit.MINUTES);
    }
}
