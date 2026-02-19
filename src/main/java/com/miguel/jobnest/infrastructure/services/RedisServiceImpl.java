package com.miguel.jobnest.infrastructure.services;

import com.miguel.jobnest.infrastructure.abstractions.services.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public <T> Optional<T> get(final String key, final Class<T> type) {
        final Object result = this.redisTemplate.opsForValue().get(key);

        if (!type.isInstance(result)) return Optional.empty();

        return Optional.of(type.cast(result));
    }

    @Override
    public <T> boolean setIfAbsent(final String key, final T value, final long timeout, final TimeUnit timeUnit) {
        return this.redisTemplate.opsForValue().setIfAbsent(key, value, timeout, timeUnit);
    }

    @Override
    public <T> void set(final String key, final T value, final long timeout, final TimeUnit timeUnit) {
        this.redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }
}
