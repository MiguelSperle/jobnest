package com.miguel.jobnest.infrastructure.configurations.security;

import com.miguel.jobnest.application.abstractions.services.JwtService;
import com.miguel.jobnest.domain.jwt.DecodedJwtToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) {
        try {
            final String jwtToken = this.recoverToken(request);

            if (jwtToken != null) {
                final DecodedJwtToken decodedJwtToken = this.jwtService.validateJwt(jwtToken);

                final List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(
                        "ROLE_" + decodedJwtToken.role()
                ));

                final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        decodedJwtToken.subject(), null, authorities
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            this.handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }

    private String recoverToken(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return null;
        }

        return authorizationHeader.replace("Bearer ", "");
    }
}
