package com.miguel.jobnest.infrastructure.idempotency;

import com.miguel.jobnest.infrastructure.abstractions.services.CacheService;
import com.miguel.jobnest.infrastructure.exceptions.IdempotencyKeyAlreadyExistsException;
import com.miguel.jobnest.infrastructure.exceptions.IdempotencyKeyRequiredException;
import com.miguel.jobnest.infrastructure.exceptions.IdempotencyKeyUnsupportedMethodException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class IdempotencyKeyFilter extends OncePerRequestFilter {
    private final CacheService cacheService;
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;
    private final HandlerExceptionResolver handlerExceptionResolver;

    public IdempotencyKeyFilter(
            final CacheService cacheService,
            final RequestMappingHandlerMapping requestMappingHandlerMapping,
            final HandlerExceptionResolver handlerExceptionResolver
    ) {
        this.cacheService = cacheService;
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    private static final String IDEMPOTENCY_KEY_REDIS_PREFIX = "idempotency-key:";

    @Override
    protected void doFilterInternal(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final FilterChain filterChain
    ) {
        try {
            final HandlerMethod handlerMethod = this.getHandlerMethod(request);

            if (handlerMethod != null && this.isIdempotencyKeyAnnotated(handlerMethod)) {
                if (!this.isSupportedMethod(request)) {
                    throw IdempotencyKeyUnsupportedMethodException.with("Idempotency key is not supported for the %s method".formatted(request.getMethod()));
                }

                final String idempotencyKeyHeader = request.getHeader(IdempotencyKey.IDEMPOTENCY_KEY_HEADER);

                if (idempotencyKeyHeader == null || idempotencyKeyHeader.isBlank()) {
                    throw IdempotencyKeyRequiredException.with("Idempotency key is required and the required header is 'x-idempotency-key'");
                }

                final String redisKey = IDEMPOTENCY_KEY_REDIS_PREFIX.concat(idempotencyKeyHeader);

                final Optional<IdempotencyKeyValue> existsIdempotencyKeyValue = this.cacheService.get(redisKey, IdempotencyKeyValue.class);

                if (existsIdempotencyKeyValue.isPresent() && existsIdempotencyKeyValue.get().isDone()) {
                    response.getWriter().write(existsIdempotencyKeyValue.get().body());
                    response.setStatus(existsIdempotencyKeyValue.get().status());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.addHeader(IdempotencyKey.IDEMPOTENCY_RESPONSE_HEADER, "true");
                    existsIdempotencyKeyValue.get().headers().forEach(response::setHeader);
                    return;
                }

                final IdempotencyKey idempotencyKeyValues = this.getIdempotencyKeyValues(handlerMethod);
                final long timeout = idempotencyKeyValues.timeout();
                final TimeUnit timeUnit = idempotencyKeyValues.timeUnit();

                final Boolean isAbsent = this.cacheService.setIfAbsent(redisKey, IdempotencyKeyValue.init(), timeout, timeUnit);

                if (Boolean.FALSE.equals(isAbsent)) {
                    throw IdempotencyKeyAlreadyExistsException.with("Idempotency key already exists");
                }

                final ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
                filterChain.doFilter(request, responseWrapper);

                if (this.isCacheableResponse(responseWrapper)) {
                    final String body = new String(responseWrapper.getContentAsByteArray(), responseWrapper.getCharacterEncoding());

                    final Map<String, String> headers = responseWrapper.getHeaderNames().stream().collect(Collectors.toMap(
                            headerName -> headerName,
                            headerName -> Objects.toString(responseWrapper.getHeader(headerName), ""),
                            (v1, v2) -> v1
                    ));

                    final IdempotencyKeyValue idempotencyKeyValue = IdempotencyKeyValue.done(
                            responseWrapper.getStatus(), body, headers
                    );

                    this.cacheService.set(redisKey, idempotencyKeyValue, timeout, timeUnit);
                } else {
                    this.cacheService.delete(redisKey);
                }

                responseWrapper.copyBodyToResponse();
            } else {
                filterChain.doFilter(request, response);
            }
        } catch (final Exception ex) {
            this.handlerExceptionResolver.resolveException(request, response, null, ex);
        }
    }

    private HandlerMethod getHandlerMethod(final HttpServletRequest request) {
        final HandlerExecutionChain handlerChain;

        try {
            handlerChain = this.requestMappingHandlerMapping.getHandler(request);
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }

        if (handlerChain != null && handlerChain.getHandler() instanceof HandlerMethod) {
            return (HandlerMethod) handlerChain.getHandler();
        }

        return null;
    }

    private boolean isIdempotencyKeyAnnotated(final HandlerMethod handlerMethod) {
        final Method method = handlerMethod.getMethod();
        return method.isAnnotationPresent(IdempotencyKey.class) && handlerMethod.getBeanType().isAnnotationPresent(RestController.class);
    }

    private IdempotencyKey getIdempotencyKeyValues(final HandlerMethod handlerMethod) {
        return handlerMethod.getMethodAnnotation(IdempotencyKey.class);
    }

    private boolean isSupportedMethod(final HttpServletRequest request) {
        return request.getMethod().equals("POST") || request.getMethod().equals("PATCH");
    }

    private boolean isCacheableResponse(final ContentCachingResponseWrapper responseWrapper) {
        return responseWrapper.getStatus() >= 200 && responseWrapper.getStatus() < 300;
    }
}
