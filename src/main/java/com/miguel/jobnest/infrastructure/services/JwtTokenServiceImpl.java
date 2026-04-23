package com.miguel.jobnest.infrastructure.services;

import com.miguel.jobnest.application.abstractions.services.JwtTokenService;
import com.miguel.jobnest.infrastructure.exceptions.AccessTokenGenerationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {
    private final JwtEncoder jwtEncoder;

    public JwtTokenServiceImpl(final JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    private static final Logger log = LoggerFactory.getLogger(JwtTokenServiceImpl.class);

    @Override
    public String generateAccessToken(final String userId, final String role, final List<String> permissions) {
        try {
            final Instant now = Instant.now();

            final JwtClaimsSet claims = JwtClaimsSet.builder()
                    .issuer("jobnest")
                    .issuedAt(now)
                    .expiresAt(now.plusSeconds(900L)) // from 5 until 15 minutes for the jwt expire
                    .subject(userId)
                    .claim("role", role)
                    .claim("permissions", permissions)
                    .build();

            return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        } catch (final Exception ex) {
            log.error("Failed to generate access token | userId: {}, role: {}, permissions: {}", userId, role, permissions, ex);
            throw new AccessTokenGenerationFailedException("Failed to generate access token", ex);
        }
    }
}
