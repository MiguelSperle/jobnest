package com.miguel.jobnest.infrastructure.services;

import com.miguel.jobnest.infrastructure.abstractions.services.CacheService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class RedisCacheService implements CacheService {
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisCacheService(final RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public <T> Optional<T> get(final String key, final Class<T> resultType) {
        final Object result = this.redisTemplate.opsForValue().get(key);

        if (!resultType.isInstance(result)) return Optional.empty();

        return Optional.of(resultType.cast(result));
    }

    @Override
    public <T> Boolean setIfAbsent(final String key, final T value, final long timeout, final TimeUnit timeUnit) {
        return this.redisTemplate.opsForValue().setIfAbsent(key, value, timeout, timeUnit);
    }

    @Override
    public <T> void set(final String key, final T value, final long timeout, final TimeUnit timeUnit) {
        this.redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    @Override
    public <T> T execute(final String script, final Class<T> resultType, final List<String> keys, final long timeout) {
        return this.redisTemplate.execute(new DefaultRedisScript<>(script, resultType), keys, timeout);
    }

    @Override
    public void delete(final String key) {
        this.redisTemplate.delete(key);
    }
}
