package com.miguel.jobnest.application.abstractions.services;

import java.time.Duration;
import java.util.Optional;

public interface CacheService {
    <T> Optional<T> get(String id, Class<T> type);
    <T> void set(String id, T value, Duration ttl);
    void evict(String id);
}



