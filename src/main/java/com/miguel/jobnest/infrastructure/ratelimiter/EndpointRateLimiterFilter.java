package com.miguel.jobnest.infrastructure.ratelimiter;

import com.miguel.jobnest.infrastructure.exceptions.TooManyRequestsException;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.Optional;

@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class EndpointRateLimiterFilter extends OncePerRequestFilter {
    private final RateLimiterRegistry rateLimiterRegistry;
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;
    private final HandlerExceptionResolver handlerExceptionResolver;

    public EndpointRateLimiterFilter(
            final RateLimiterRegistry rateLimiterRegistry,
            final RequestMappingHandlerMapping requestMappingHandlerMapping,
            final HandlerExceptionResolver handlerExceptionResolver
    ) {
        this.rateLimiterRegistry = rateLimiterRegistry;
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final FilterChain filterChain
    ) {
        try {
            final HandlerMethod handlerMethod = this.getHandlerMethod(request);

            if (handlerMethod != null && this.isEndpointRateLimiterAnnotated(handlerMethod)) {
                final EndpointRateLimiter endpointRateLimiterValue = this.getCustomRateLimiterValues(handlerMethod);

                final String instanceName = endpointRateLimiterValue.instanceName();

                final Optional<RateLimiter> rateLimiter = this.rateLimiterRegistry.find(instanceName);

                if (rateLimiter.isPresent() && !rateLimiter.get().acquirePermission()) {
                    throw TooManyRequestsException.with("Too many requests occurred - Rate Limit Exceeded. Wait a moment before trying again");
                }

                filterChain.doFilter(request, response);
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

    private boolean isEndpointRateLimiterAnnotated(final HandlerMethod handlerMethod) {
        final Method method = handlerMethod.getMethod();
        return method.isAnnotationPresent(EndpointRateLimiter.class) && handlerMethod.getBeanType().isAnnotationPresent(RestController.class);
    }

    private EndpointRateLimiter getCustomRateLimiterValues(final HandlerMethod handlerMethod) {
        return handlerMethod.getMethodAnnotation(EndpointRateLimiter.class);
    }
}
