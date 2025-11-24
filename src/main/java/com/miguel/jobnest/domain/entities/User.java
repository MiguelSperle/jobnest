package com.miguel.jobnest.domain.entities;

import com.miguel.jobnest.domain.enums.AuthorizationRole;
import com.miguel.jobnest.domain.enums.UserStatus;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

public class User implements Serializable {
    private final String id;
    private final String name;
    private final String email;
    private final String description;
    private final String password;
    private final UserStatus userStatus;
    private final AuthorizationRole authorizationRole;
    private final String city;
    private final String state;
    private final String country;
    private final LocalDateTime createdAt;

    private User(
            String id,
            String name,
            String email,
            String description,
            String password,
            UserStatus userStatus,
            AuthorizationRole authorizationRole,
            String city,
            String state,
            String country,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.description = description;
        this.password = password;
        this.userStatus = userStatus;
        this.authorizationRole = authorizationRole;
        this.city = city;
        this.state = state;
        this.country = country;
        this.createdAt = createdAt;
    }

    public static User newUser(
            String name,
            String email,
            String password,
            AuthorizationRole authorizationRole,
            String city,
            String state,
            String country
    ) {
        return new User(
                IdentifierUtils.generateUUID(),
                name,
                email,
                null,
                password,
                UserStatus.UNVERIFIED,
                authorizationRole,
                city,
                state,
                country,
                TimeUtils.now()
        );
    }

    public static User with(
            String id,
            String name,
            String email,
            String description,
            String password,
            UserStatus userStatus,
            AuthorizationRole authorizationRole,
            String city,
            String state,
            String country,
            LocalDateTime createdAt
    ) {
        return new User(
                id,
                name,
                email,
                description,
                password,
                userStatus,
                authorizationRole,
                city,
                state,
                country,
                createdAt
        );
    }

    public User withName(String name) {
        return new User(
                this.id,
                name,
                this.email,
                this.description,
                this.password,
                this.userStatus,
                this.authorizationRole,
                this.city,
                this.state,
                this.country,
                this.createdAt
        );
    }

    public User withEmail(String email) {
        return new User(
                this.id,
                this.name,
                email,
                this.description,
                this.password,
                this.userStatus,
                this.authorizationRole,
                this.city,
                this.state,
                this.country,
                this.createdAt
        );
    }

    public User withDescription(String description) {
        return new User(
                this.id,
                this.name,
                this.email,
                description,
                this.password,
                this.userStatus,
                this.authorizationRole,
                this.city,
                this.state,
                this.country,
                this.createdAt
        );
    }

    public User withCity(String city) {
        return new User(
                this.id,
                this.name,
                this.email,
                this.description,
                this.password,
                this.userStatus,
                this.authorizationRole,
                city,
                this.state,
                this.country,
                this.createdAt
        );
    }

    public User withState(String state) {
        return new User(
                this.id,
                this.name,
                this.email,
                this.description,
                this.password,
                this.userStatus,
                this.authorizationRole,
                this.city,
                state,
                this.country,
                this.createdAt
        );
    }

    public User withCountry(String country) {
        return new User(
                this.id,
                this.name,
                this.email,
                this.description,
                this.password,
                this.userStatus,
                this.authorizationRole,
                this.city,
                this.state,
                country,
                this.createdAt
        );
    }

    public User withUserStatus(UserStatus userStatus) {
        return new User(
                this.id,
                this.name,
                this.email,
                this.description,
                this.password,
                userStatus,
                this.authorizationRole,
                this.city,
                this.state,
                this.country,
                this.createdAt
        );
    }

    public User withPassword(String password) {
        return new User(
                this.id,
                this.name,
                this.email,
                this.description,
                password,
                this.userStatus,
                this.authorizationRole,
                this.city,
                this.state,
                this.country,
                this.createdAt
        );
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getDescription() {
        return this.description;
    }

    public String getPassword() {
        return this.password;
    }

    public UserStatus getUserStatus() {
        return this.userStatus;
    }

    public AuthorizationRole getAuthorizationRole() {
        return this.authorizationRole;
    }

    public String getCity() {
        return this.city;
    }

    public String getState() {
        return this.state;
    }

    public String getCountry() {
        return this.country;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + this.id + '\'' +
                ", name='" + this.name + '\'' +
                ", email='" + this.email + '\'' +
                ", description='" + this.description + '\'' +
                ", password='" + this.password + '\'' +
                ", userStatus=" + this.userStatus +
                ", authorizationRole=" + this.authorizationRole +
                ", city='" + this.city + '\'' +
                ", state='" + this.state + '\'' +
                ", country='" + this.country + '\'' +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
