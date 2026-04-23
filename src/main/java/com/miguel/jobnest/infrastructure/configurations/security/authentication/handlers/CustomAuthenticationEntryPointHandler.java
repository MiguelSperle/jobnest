package com.miguel.jobnest.infrastructure.configurations.security.authentication.handlers;

import com.miguel.jobnest.domain.utils.TimeUtils;
import com.miguel.jobnest.infrastructure.configurations.json.Json;
import com.miguel.jobnest.infrastructure.utils.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final AuthenticationException ex
    ) throws IOException {
        String message = "Access token is required";

        final Throwable cause = ex.getCause();

        if (cause instanceof JwtValidationException) {
            message = "Access token is expired";
        } else if (cause instanceof BadJwtException) {
            message = "Access token is invalid";
        }

        final int status = HttpStatus.UNAUTHORIZED.value();

        final ApiError apiError = ApiError.from(message, status, TimeUtils.now());

        response.getWriter().write(Json.writeValueAsString(apiError));
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }
}
