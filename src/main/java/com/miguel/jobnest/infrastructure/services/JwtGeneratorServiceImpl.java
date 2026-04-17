package com.miguel.jobnest.infrastructure.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.miguel.jobnest.application.abstractions.services.JwtGeneratorService;
import com.miguel.jobnest.infrastructure.exceptions.JwtTokenGenerationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class JwtGeneratorServiceImpl implements JwtGeneratorService {
    @Value("${security.token.secret}")
    private String secret;

    private static final Logger log = LoggerFactory.getLogger(JwtGeneratorServiceImpl.class);

    @Override
    public String generateJwt(final String userId, final String role) {
        try {
            final Instant now = Instant.now();
            final Algorithm algorithm = Algorithm.HMAC256(this.secret);

            return JWT.create()
                    .withIssuer("jobnest")
                    .withSubject(userId)
                    .withClaim("role", role)
                    .withExpiresAt(now.plusSeconds(900))
                    .sign(algorithm);
        } catch (final Exception ex) {
            log.error("Failed to generate JWT token", ex);
            throw JwtTokenGenerationFailedException.with("Failed to generate JWT token");
        }
    }
}
