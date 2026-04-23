package com.miguel.jobnest.domain.utils;

import java.time.LocalDateTime;

public final class TimeUtils {
    private TimeUtils() {
    }

    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    public static boolean isExpired(final LocalDateTime expirationTime, final LocalDateTime now) {
        return now.isAfter(expirationTime);
    }
}
