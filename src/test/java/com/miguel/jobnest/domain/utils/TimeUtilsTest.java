package com.miguel.jobnest.domain.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class TimeUtilsTest {
    @Test
    void shouldReturnTime_whenCallNow() {
        final LocalDateTime now = TimeUtils.now();

        Assertions.assertNotNull(now);
    }

    @Test
    void shouldReturnTrue_whenTimeIsExpired() {
        final boolean isExpired = TimeUtils.isExpired(TimeUtils.now().minusDays(1), TimeUtils.now());

        Assertions.assertTrue(isExpired);
    }
}
