package com.miguel.jobnest.domain.entities;

import com.miguel.jobnest.domain.enums.AuthorizationRole;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;

import java.time.LocalDateTime;

public class User {
    private final String id;
    private final String fullName;
    private final String email;
    private final String phoneNumber;
    private final String password;
    private final String curriculumUrl;
    private final AuthorizationRole authorizationRole;
    private final LocalDateTime createdAt;

    private User(
            String id,
            String fullName,
            String email,
            String phoneNumber,
            String password,
            String curriculumUrl,
            AuthorizationRole authorizationRole,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.curriculumUrl = curriculumUrl;
        this.authorizationRole = authorizationRole;
        this.createdAt = createdAt;
    }

    public static User newUser(
        String fullName,
        String email,
        String phoneNumber,
        String password,
        String curriculumUrl
    ) {
        return new User(
                IdentifierUtils.generateUUID(),
                fullName,
                email,
                phoneNumber,
                password,
                curriculumUrl,
                AuthorizationRole.CANDIDATE,
                TimeUtils.now()
        );
    }

    public static User with(
            String id,
            String fullName,
            String email,
            String phoneNumber,
            String password,
            String curriculumUrl,
            AuthorizationRole authorizationRole,
            LocalDateTime createdAt
    ) {
        return new User(
                id,
                fullName,
                email,
                phoneNumber,
                password,
                curriculumUrl,
                authorizationRole,
                createdAt
        );
    }

    public String getId() {
        return this.id;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getPassword() {
        return this.password;
    }

    public String getCurriculumUrl() {
        return this.curriculumUrl;
    }

    public AuthorizationRole getAuthorizationRole() {
        return this.authorizationRole;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + this.id + '\'' +
                ", fullName='" + this.fullName + '\'' +
                ", email='" + this.email + '\'' +
                ", phoneNumber='" + this.phoneNumber + '\'' +
                ", password='" + this.password + '\'' +
                ", curriculumUrl='" + this.curriculumUrl + '\'' +
                ", authorizationRole=" + this.authorizationRole +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
