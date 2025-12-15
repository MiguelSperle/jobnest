package com.miguel.jobnest.domain.entities;

import com.miguel.jobnest.domain.enums.AuthorizationRole;
import com.miguel.jobnest.domain.enums.UserStatus;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class UserTest {

    @Test
    void shouldReturnUser_whenCallNewUser() {
        final String name = "Leonardo Jardim";
        final String email = "leo1898@gmail.com";
        final String password = "12345";
        final AuthorizationRole authorizationRole = AuthorizationRole.CANDIDATE;
        final String city = "Rio de Janeiro";
        final String state = "Rio de Janeiro";
        final String country = "Brazil";

        final User newUser = User.newUser(
                name,
                email,
                password,
                authorizationRole,
                city,
                state,
                country
        );

        Assertions.assertNotNull(newUser);
        Assertions.assertNotNull(newUser.getId());
        Assertions.assertEquals(name, newUser.getName());
        Assertions.assertEquals(email, newUser.getEmail());
        Assertions.assertNull(newUser.getDescription());
        Assertions.assertEquals(password, newUser.getPassword());
        Assertions.assertEquals(UserStatus.UNVERIFIED, newUser.getUserStatus());
        Assertions.assertEquals(authorizationRole, newUser.getAuthorizationRole());
        Assertions.assertEquals(city, newUser.getCity());
        Assertions.assertEquals(state, newUser.getState());
        Assertions.assertEquals(country, newUser.getCountry());
        Assertions.assertNotNull(newUser.getCreatedAt());
    }

    @Test
    void shouldReturnUser_whenCallWith() {
        final String id = IdentifierUtils.generateUUID();
        final String name = "Leonardo Jardim";
        final String email = "leo1898@gmail.com";
        final String description = "My description";
        final String password = "12345";
        final UserStatus userStatus = UserStatus.UNVERIFIED;
        final AuthorizationRole authorizationRole = AuthorizationRole.CANDIDATE;
        final String city = "Rio de Janeiro";
        final String state = "Rio de Janeiro";
        final String country = "Brazil";
        final LocalDateTime createdAt = TimeUtils.now();

        final User user = User.with(
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

        Assertions.assertNotNull(user);
        Assertions.assertEquals(id, user.getId());
        Assertions.assertEquals(name, user.getName());
        Assertions.assertEquals(email, user.getEmail());
        Assertions.assertEquals(description, user.getDescription());
        Assertions.assertEquals(password, user.getPassword());
        Assertions.assertEquals(userStatus, user.getUserStatus());
        Assertions.assertEquals(authorizationRole, user.getAuthorizationRole());
        Assertions.assertEquals(city, user.getCity());
        Assertions.assertEquals(state, user.getState());
        Assertions.assertEquals(country, user.getCountry());
        Assertions.assertEquals(createdAt, user.getCreatedAt());
    }

    @Test
    void shouldReturnUpdatedUser_whenCallWithName() {
        final String id = IdentifierUtils.generateUUID();
        final String name = "Leonardo Jardim";
        final String email = "leo1898@gmail.com";
        final String description = "My description";
        final String password = "12345";
        final UserStatus userStatus = UserStatus.UNVERIFIED;
        final AuthorizationRole authorizationRole = AuthorizationRole.CANDIDATE;
        final String city = "Rio de Janeiro";
        final String state = "Rio de Janeiro";
        final String country = "Brazil";
        final LocalDateTime createdAt = TimeUtils.now();

        final User user = User.with(
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

        final String newName = "Leo Jardim";

        final User updatedUser = user.withName(newName);

        Assertions.assertEquals(newName, updatedUser.getName());
    }

    @Test
    void shouldReturnUpdatedUser_whenCallWithEmail() {
        final String id = IdentifierUtils.generateUUID();
        final String name = "Leonardo Jardim";
        final String email = "leo1898@gmail.com";
        final String description = "My description";
        final String password = "12345";
        final UserStatus userStatus = UserStatus.UNVERIFIED;
        final AuthorizationRole authorizationRole = AuthorizationRole.CANDIDATE;
        final String city = "Rio de Janeiro";
        final String state = "Rio de Janeiro";
        final String country = "Brazil";
        final LocalDateTime createdAt = TimeUtils.now();

        final User user = User.with(
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

        final String newEmail = "leojardim@gmail.com";

        final User updatedUser = user.withEmail(newEmail);

        Assertions.assertEquals(newEmail, updatedUser.getEmail());
    }

    @Test
    void shouldReturnUpdatedUser_whenCallWithDescription() {
        final String id = IdentifierUtils.generateUUID();
        final String name = "Leonardo Jardim";
        final String email = "leo1898@gmail.com";
        final String description = "My description";
        final String password = "12345";
        final UserStatus userStatus = UserStatus.UNVERIFIED;
        final AuthorizationRole authorizationRole = AuthorizationRole.CANDIDATE;
        final String city = "Rio de Janeiro";
        final String state = "Rio de Janeiro";
        final String country = "Brazil";
        final LocalDateTime createdAt = TimeUtils.now();

        final User user = User.with(
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

        final String newDescription = "My description about me";

        final User updatedUser = user.withDescription(newDescription);

        Assertions.assertEquals(newDescription, updatedUser.getDescription());
    }

    @Test
    void shouldReturnUpdatedUser_whenCallWithCity() {
        final String id = IdentifierUtils.generateUUID();
        final String name = "Leonardo Jardim";
        final String email = "leo1898@gmail.com";
        final String description = "My description";
        final String password = "12345";
        final UserStatus userStatus = UserStatus.UNVERIFIED;
        final AuthorizationRole authorizationRole = AuthorizationRole.CANDIDATE;
        final String city = "Rio de Janeiro";
        final String state = "Rio de Janeiro";
        final String country = "Brazil";
        final LocalDateTime createdAt = TimeUtils.now();

        final User user = User.with(
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

        final String newCity = "São Paulo";

        final User updatedUser = user.withCity(newCity);

        Assertions.assertEquals(newCity, updatedUser.getCity());
    }

    @Test
    void shouldReturnUpdatedUser_whenCallWithState() {
        final String id = IdentifierUtils.generateUUID();
        final String name = "Leonardo Jardim";
        final String email = "leo1898@gmail.com";
        final String description = "My description";
        final String password = "12345";
        final UserStatus userStatus = UserStatus.UNVERIFIED;
        final AuthorizationRole authorizationRole = AuthorizationRole.CANDIDATE;
        final String city = "Rio de Janeiro";
        final String state = "Rio de Janeiro";
        final String country = "Brazil";
        final LocalDateTime createdAt = TimeUtils.now();

        final User user = User.with(
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

        final String newState = "São Paulo";

        final User updatedUser = user.withState(newState);

        Assertions.assertEquals(newState, updatedUser.getState());
    }

    @Test
    void shouldReturnUpdatedUser_whenCallWithCountry() {
        final String id = IdentifierUtils.generateUUID();
        final String name = "Leonardo Jardim";
        final String email = "leo1898@gmail.com";
        final String description = "My description";
        final String password = "12345";
        final UserStatus userStatus = UserStatus.UNVERIFIED;
        final AuthorizationRole authorizationRole = AuthorizationRole.CANDIDATE;
        final String city = "Rio de Janeiro";
        final String state = "Rio de Janeiro";
        final String country = "Brazil";
        final LocalDateTime createdAt = TimeUtils.now();

        final User user = User.with(
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

        final String newCountry = "Brazil";

        final User updatedUser = user.withCountry(newCountry);

        Assertions.assertEquals(newCountry, updatedUser.getCountry());
    }

    @Test
    void shouldReturnUpdatedUser_whenCallWithUserStatus() {
        final String id = IdentifierUtils.generateUUID();
        final String name = "Leonardo Jardim";
        final String email = "leo1898@gmail.com";
        final String description = "My description";
        final String password = "12345";
        final UserStatus userStatus = UserStatus.UNVERIFIED;
        final AuthorizationRole authorizationRole = AuthorizationRole.CANDIDATE;
        final String city = "Rio de Janeiro";
        final String state = "Rio de Janeiro";
        final String country = "Brazil";
        final LocalDateTime createdAt = TimeUtils.now();

        final User user = User.with(
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

        final UserStatus newUserStatus = UserStatus.VERIFIED;

        final User updatedUser = user.withUserStatus(newUserStatus);

        Assertions.assertEquals(newUserStatus, updatedUser.getUserStatus());
    }

    @Test
    void shouldReturnUpdatedUser_whenCallWithPassword() {
        final String id = IdentifierUtils.generateUUID();
        final String name = "Leonardo Jardim";
        final String email = "leo1898@gmail.com";
        final String description = "My description";
        final String password = "12345";
        final UserStatus userStatus = UserStatus.UNVERIFIED;
        final AuthorizationRole authorizationRole = AuthorizationRole.CANDIDATE;
        final String city = "Rio de Janeiro";
        final String state = "Rio de Janeiro";
        final String country = "Brazil";
        final LocalDateTime createdAt = TimeUtils.now();

        final User user = User.with(
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

        final String newPassword = "123456";

        final User updatedUser = user.withPassword(newPassword);

        Assertions.assertEquals(newPassword, updatedUser.getPassword());
    }

    @Test
    void shouldReturnFormattedUser_whenCallToString() {
        final String id = IdentifierUtils.generateUUID();
        final String name = "Leonardo Jardim";
        final String email = "leo1898@gmail.com";
        final String description = "My description";
        final String password = "12345";
        final UserStatus userStatus = UserStatus.UNVERIFIED;
        final AuthorizationRole authorizationRole = AuthorizationRole.CANDIDATE;
        final String city = "Rio de Janeiro";
        final String state = "Rio de Janeiro";
        final String country = "Brazil";
        final LocalDateTime createdAt = TimeUtils.now();

        final User user = User.with(
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

        final String expectedToString = "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                ", password='" + password + '\'' +
                ", userStatus=" + userStatus +
                ", authorizationRole=" + authorizationRole +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", createdAt=" + createdAt +
                '}';

        Assertions.assertEquals(expectedToString, user.toString());
    }
}
