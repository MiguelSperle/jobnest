package com.miguel.jobnest.infrastructure.services;

import com.miguel.jobnest.infrastructure.abstractions.services.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class RedisServiceImpl implements RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisServiceImpl(final RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

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

    @Override
    public long execute(final String script, final List<String> keys, final int seconds) {
        return this.redisTemplate.execute(new DefaultRedisScript<>(script, long.class), keys, seconds);
    }
}
