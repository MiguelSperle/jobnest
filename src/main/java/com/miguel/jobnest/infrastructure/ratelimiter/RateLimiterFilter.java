package com.miguel.jobnest.infrastructure.ratelimiter;

import com.miguel.jobnest.infrastructure.abstractions.services.RedisService;
import com.miguel.jobnest.infrastructure.exceptions.TooManyRequestsException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.List;

@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class RateLimiterFilter extends OncePerRequestFilter {
    private final RedisService redisService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    public RateLimiterFilter(
            final RedisService redisService,
            final HandlerExceptionResolver handlerExceptionResolver
    ) {
        this.redisService = redisService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    private static final int MAX_REQUESTS_ALLOWED = 50;

    private static final int FIXED_WINDOW_SECONDS = 60;
    private static final String FIXED_WINDOW_SCRIPT = """
            local key = KEYS[1]
            local seconds = tonumber(ARGV[1])
            
            local count = redis.call('INCR', key)
            
            if count == 1 then
                redis.call('EXPIRE', key, seconds)
            end
            
            return count
            """;

    private static final String RATE_LIMIT_REDIS_PREFIX = "rate-limit:";

    private static final Logger log = LoggerFactory.getLogger(RateLimiterFilter.class);

    @Override
    protected void doFilterInternal(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final FilterChain filterChain
    ) {
        try {
            log.info("Processing rate limiter filter");

            final String clientIp = this.extractClientIp(request);
            final String redisKey = RATE_LIMIT_REDIS_PREFIX.concat(clientIp);

            final long count = this.redisService.execute(FIXED_WINDOW_SCRIPT, List.of(redisKey), FIXED_WINDOW_SECONDS);

            if (count > MAX_REQUESTS_ALLOWED) {
                throw TooManyRequestsException.with("Too many requests occurred. Please wait a moment before trying again");
            }

            filterChain.doFilter(request, response);
        } catch (final Exception ex) {
            this.handlerExceptionResolver.resolveException(request, response, null, ex);
        }
    }

    private String extractClientIp(HttpServletRequest request) {
        final String xForwardedForHeader = request.getHeader("X-Forwarded-For");

        if (xForwardedForHeader != null && !xForwardedForHeader.isBlank()) {
            return xForwardedForHeader.split(",")[0].trim();
        }

        final String xRealIpHeader = request.getHeader("X-Real-IP");

        if (xRealIpHeader != null && !xRealIpHeader.isBlank()) {
            return xRealIpHeader.trim();
        }

        return request.getRemoteAddr();
    }
}
