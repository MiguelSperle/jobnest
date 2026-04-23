package com.miguel.jobnest.infrastructure.configurations.security.authentication;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    @Override
    public AbstractAuthenticationToken convert(final Jwt jwt) {
        final List<GrantedAuthority> authorities = new ArrayList<>();

        final String role = jwt.getClaimAsString("role");

        if (role != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }

        final List<String> permissions = jwt.getClaimAsStringList("permissions");

        if (!permissions.isEmpty()) {
            authorities.addAll(permissions.stream().map(SimpleGrantedAuthority::new).toList());
        }

        return new JwtAuthenticationToken(jwt, authorities);
    }
}