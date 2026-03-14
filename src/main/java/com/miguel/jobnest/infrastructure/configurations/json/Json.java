package com.miguel.jobnest.infrastructure.configurations.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.blackbird.BlackbirdModule;

import java.util.concurrent.Callable;

public enum Json {
    INSTANCE;

    public static ObjectMapper mapper() {
        return INSTANCE.mapper.copy();
    }

    public static byte[] writeValueAsBytes(final Object obj) {
        return invoke(() -> INSTANCE.mapper.writeValueAsBytes(obj));
    }

    public static String writeValueAsString(final Object obj) {
        return invoke(() -> INSTANCE.mapper.writeValueAsString(obj));
    }

    public static <T> T readValue(final byte[] json, final Class<T> clazz) {
        return invoke(() -> INSTANCE.mapper.readValue(json, clazz));
    }

    public static <T> T readValue(final String json, final Class<T> clazz) {
        return invoke(() -> INSTANCE.mapper.readValue(json, clazz));
    }

    public static <T> T readValue(final String json, final TypeReference<T> clazz) {
        return invoke(() -> INSTANCE.mapper.readValue(json, clazz));
    }

    public static <T> T readTree(final String json, final Class<T> clazz) {
        return invoke(() -> {
            final JsonNode jsonNode = INSTANCE.mapper.readTree(json);

            if (jsonNode instanceof TextNode) {
                return readTree(jsonNode.asText(), clazz);
            }

            return INSTANCE.mapper.convertValue(jsonNode, clazz);
        });
    }

    private final ObjectMapper mapper;

    Json() {
        this.mapper = new ObjectMapper()
                .setDateFormat(new StdDateFormat())
                .setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE)
                .registerModules(new JavaTimeModule(), new Jdk8Module(), new BlackbirdModule())
                .disable(
                        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                        DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES,
                        DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES
                )
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    private static <T> T invoke(final Callable<T> callable) {
        try {
            return callable.call();
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
