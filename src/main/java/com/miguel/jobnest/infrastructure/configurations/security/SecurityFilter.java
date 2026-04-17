package com.miguel.jobnest.infrastructure.configurations.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.miguel.jobnest.infrastructure.abstractions.services.JwtDecoderService;
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
    private final JwtDecoderService jwtDecoderService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final FilterChain filterChain
    ) {
        try {
            final String jwtToken = this.recoverToken(request);

            if (jwtToken != null) {
                final DecodedJWT decodedJWT = this.jwtDecoderService.decodeJwt(jwtToken);

                final String role = decodedJWT.getClaim("role").asString();
                final String userId = decodedJWT.getSubject();

                final List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(
                        "ROLE_" + role
                ));

                final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userId, null, authorities
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
