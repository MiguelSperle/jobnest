package com.miguel.jobnest.infrastructure.configurations.security.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.io.OutputStream;
import java.util.Collections;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final AccessDeniedException accessDeniedException
    ) throws IOException {
        final ApiError apiError = new ApiError(
                Collections.singletonList("Access denied. You don't have permission to access this resource"),
                HttpStatus.FORBIDDEN.getReasonPhrase()
        );

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());

        final OutputStream responseStream = response.getOutputStream();
        final ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(responseStream, apiError);
        responseStream.flush();
    }
}
