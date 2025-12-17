package com.miguel.jobnest.domain.utils;

import java.util.UUID;

public class IdentifierUtils {
    public static String generateNewId() {
        return UUID.randomUUID().toString();
    }
}
