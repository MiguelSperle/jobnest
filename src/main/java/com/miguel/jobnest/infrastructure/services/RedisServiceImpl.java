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
    public <T> Optional<T> get(String key, Class<T> type) {
        final Object result = this.redisTemplate.opsForValue().get(key);

        if (!type.isInstance(result)) return Optional.empty();

        return Optional.of(type.cast(result));
    }

    @Override
    public <T> boolean setIfAbsent(String key, T value, long ttl, TimeUnit timeUnit) {
        return this.redisTemplate.opsForValue().setIfAbsent(key, value, ttl, timeUnit);
    }

    @Override
    public <T> void set(String key, T value, long ttl, TimeUnit timeUnit) {
        this.redisTemplate.opsForValue().set(key, value, ttl, timeUnit);
    }
}
