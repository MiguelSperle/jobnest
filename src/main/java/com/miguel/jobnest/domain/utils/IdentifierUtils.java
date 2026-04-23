package com.miguel.jobnest.domain.utils;

import java.util.UUID;

public final class IdentifierUtils {
    private IdentifierUtils() {
    }

    public static String generateNewId() {
        return UUID.randomUUID().toString();
    }
}
