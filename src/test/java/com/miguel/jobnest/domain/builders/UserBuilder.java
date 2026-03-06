package com.miguel.jobnest.domain.builders;

import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.enums.AuthorizationRole;
import com.miguel.jobnest.domain.enums.UserStatus;

import java.time.LocalDateTime;

public class UserBuilder {
    private String id;
    private String name;
    private String email;
    private String description;
    private String password;
    private UserStatus userStatus;
    private AuthorizationRole authorizationRole;
    private String city;
    private String state;
    private String country;
    private LocalDateTime createdAt;

    public static UserBuilder user() {
        return new UserBuilder();
    }

    public UserBuilder id(String id) {
        this.id = id;
        return this;
    }

    public UserBuilder name(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder description(String description) {
        this.description = description;
        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder userStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
        return this;
    }

    public UserBuilder authorizationRole(AuthorizationRole authorizationRole) {
        this.authorizationRole = authorizationRole;
        return this;
    }

    public UserBuilder city(String city) {
        this.city = city;
        return this;
    }

    public UserBuilder state(String state) {
        this.state = state;
        return this;
    }

    public UserBuilder country(String country) {
        this.country = country;
        return this;
    }

    public UserBuilder createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public User build() {
        return User.with(
                this.id,
                this.name,
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
}
