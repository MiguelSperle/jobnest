package com.miguel.jobnest.infrastructure.configurations.security.authentication.handlers;

import com.miguel.jobnest.domain.utils.TimeUtils;
import com.miguel.jobnest.infrastructure.configurations.json.Json;
import com.miguel.jobnest.infrastructure.utils.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final AccessDeniedException ex
    ) throws IOException {
        final int status = HttpStatus.FORBIDDEN.value();

        final ApiError apiError = ApiError.from("Access denied", status, TimeUtils.now());

        response.getWriter().write(Json.writeValueAsString(apiError));
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }
}
