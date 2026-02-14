package com.miguel.jobnest.infrastructure.abstractions.services;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public interface RedisService {
    <T> Optional<T> get(String key, Class<T> type);
    <T> void set(String key, T value, long ttl, TimeUnit timeUnit);
    <T> boolean setIfAbsent(String key, T value, long ttl, TimeUnit timeUnit);
}
