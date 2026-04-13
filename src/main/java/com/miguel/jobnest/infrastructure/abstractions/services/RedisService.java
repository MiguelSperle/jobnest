package com.miguel.jobnest.infrastructure.abstractions.services;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public interface RedisService {
    <T> Optional<T> get(String key, Class<T> type);
    <T> void set(String key, T value, long timeout, TimeUnit timeUnit);
    <T> Boolean setIfAbsent(String key, T value, long timeout, TimeUnit timeUnit);
    <T> T execute(String script, Class<T> resultType, List<String> keys, long timeout);
}
