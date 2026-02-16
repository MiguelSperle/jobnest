package com.miguel.jobnest.infrastructure.idempotency;

import com.miguel.jobnest.infrastructure.abstractions.services.RedisService;
import com.miguel.jobnest.infrastructure.exceptions.IdempotencyKeyProcessingException;
import com.miguel.jobnest.infrastructure.exceptions.IdempotencyKeyRequiredException;
import com.miguel.jobnest.infrastructure.exceptions.IdempotencyKeyUnsupportedMethodException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class IdempotencyFilter extends OncePerRequestFilter {
    private final RedisService redisService;
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;
    private final HandlerExceptionResolver handlerExceptionResolver;

    private static final String IDEMPOTENCY_KEY_REDIS_PREFIX = "idempotency-key:";

    private static final Logger log = LoggerFactory.getLogger(IdempotencyFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            log.info("Processing idempotency key filter");

            final HandlerMethod handlerMethod = this.getHandlerMethod(request);

            if (handlerMethod != null && this.isIdempotencyKeyAnnotated(handlerMethod)) {
                final String httpMethod = request.getMethod();

                if (!httpMethod.equals("POST") && !httpMethod.equals("PATCH")) {
                    throw IdempotencyKeyUnsupportedMethodException.with("Idempotency key is only supported for POST and PATCH requests");
                }

                final String idempotencyKeyHeader = request.getHeader(Idempotency.IDEMPOTENCY_KEY_HEADER);

                if (idempotencyKeyHeader == null || idempotencyKeyHeader.isEmpty()) {
                    throw IdempotencyKeyRequiredException.with("Idempotency key is required and the required header is 'x-idempotency-key'");
                }

                final String redisKey = IDEMPOTENCY_KEY_REDIS_PREFIX.concat(idempotencyKeyHeader);

                final Optional<IdempotencyValue> existsIdempotencyKeyValue = this.redisService.get(redisKey, IdempotencyValue.class);

                if (existsIdempotencyKeyValue.isPresent() && existsIdempotencyKeyValue.get().isDone()) {
                    response.setStatus(existsIdempotencyKeyValue.get().statusCode());
                    response.getWriter().write(existsIdempotencyKeyValue.get().body());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.addHeader(Idempotency.IDEMPOTENCY_RESPONSE_HEADER, "true");
                    existsIdempotencyKeyValue.get().headers().forEach(response::setHeader);
                    log.info("Idempotency key found, returning the previous response: {}", existsIdempotencyKeyValue.get());
                    return;
                }

                final Idempotency idempotencyValues = this.getIdempotencyKeyValues(handlerMethod);
                final long timeout = idempotencyValues.timeout();
                final TimeUnit timeUnit = idempotencyValues.timeUnit();

                log.info("Idempotency key not found, saving before processing the request");

                final boolean isAbsent = this.redisService.setIfAbsent(redisKey, IdempotencyValue.init(), timeout, timeUnit);

                if (!isAbsent) {
                    throw IdempotencyKeyProcessingException.with("This idempotency key is already being processed by another request");
                }

                final ContentCachingResponseWrapper contentCachingResponseWrapper = new ContentCachingResponseWrapper(response);
                filterChain.doFilter(request, contentCachingResponseWrapper);

                final byte[] bodyArray = contentCachingResponseWrapper.getContentAsByteArray();
                contentCachingResponseWrapper.copyBodyToResponse();

                final String body = new String(bodyArray, contentCachingResponseWrapper.getCharacterEncoding());

                final Map<String, String> headers = contentCachingResponseWrapper.getHeaderNames().stream().collect(Collectors.toMap(
                        headerName -> headerName,
                        headerName -> String.join(", ", contentCachingResponseWrapper.getHeaders(headerName)),
                        (exists, duplicated) -> duplicated
                ));

                final IdempotencyValue idempotencyValue = IdempotencyValue.done(
                        contentCachingResponseWrapper.getStatus(), body, headers
                );

                log.info("Idempotency key not found, saving the response for future requests, result: {}", idempotencyValue);
                this.redisService.set(redisKey, idempotencyValue, timeout, timeUnit);
            } else {
                filterChain.doFilter(request, response);
            }
        } catch (Exception ex) {
            this.handlerExceptionResolver.resolveException(request, response, null, ex);
        }
    }

    private HandlerMethod getHandlerMethod(HttpServletRequest request) {
        final HandlerExecutionChain handlerChain;

        try {
            handlerChain = this.requestMappingHandlerMapping.getHandler(request);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        if (handlerChain != null && handlerChain.getHandler() instanceof HandlerMethod) {
            return (HandlerMethod) handlerChain.getHandler();
        }

        return null;
    }

    private boolean isIdempotencyKeyAnnotated(HandlerMethod handlerMethod) {
        final Method method = handlerMethod.getMethod();
        return method.isAnnotationPresent(Idempotency.class) && handlerMethod.getBeanType().isAnnotationPresent(RestController.class);
    }

    private Idempotency getIdempotencyKeyValues(final HandlerMethod handlerMethod) {
        return handlerMethod.getMethodAnnotation(Idempotency.class);
    }
}
