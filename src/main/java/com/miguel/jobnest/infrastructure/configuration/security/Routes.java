package com.miguel.jobnest.infrastructure.configuration.security;

public class Routes {
    public static String[] USER_AUTHENTICATED_ENDPOINTS = {
            "/api/v1/users/me",
            "/api/v1/users/{id}/information",
            "/api/v1/users/{id}/password",
            "/api/v1/users/{id}/deletion"
    };
}
