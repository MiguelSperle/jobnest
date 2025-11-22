package com.miguel.jobnest.infrastructure.services;

import com.miguel.jobnest.application.abstractions.services.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService {
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public <T> Optional<T> get(String id, Class<T> type) {
        return Optional.ofNullable(type.cast(this.redisTemplate.opsForValue().get(id)));
    }

    @Override
    public <T> void set(String id, T value, Duration ttl) {
        this.redisTemplate.opsForValue().set(id, value, ttl);
    }

    @Override
    public <T> void setWithoutTTL(String id, T value) {
        this.redisTemplate.opsForValue().set(id, value);
    }

    @Override
    public void evict(String id) {
        this.redisTemplate.delete(id);
    }
}
