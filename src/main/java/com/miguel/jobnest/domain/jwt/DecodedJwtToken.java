package com.miguel.jobnest.domain.jwt;

public record DecodedJwtToken(
        String subject,
        String role
) {
}
