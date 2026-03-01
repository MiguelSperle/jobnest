package com.miguel.jobnest.domain.entities;

import com.miguel.jobnest.domain.enums.AuthorizationRole;
import com.miguel.jobnest.domain.enums.UserStatus;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;
import com.miguel.jobnest.testsupport.builders.domain.UserTestBuilder;
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
        final String id = IdentifierUtils.generateNewId();
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
        final User user = UserTestBuilder.aUser().userStatus(UserStatus.UNVERIFIED).authorizationRole(AuthorizationRole.CANDIDATE).build();

        final String newName = "Leo Jardim";

        final User updatedUser = user.withName(newName);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(newName, updatedUser.getName());
    }

    @Test
    void shouldReturnUpdatedUser_whenCallWithEmail() {
        final User user = UserTestBuilder.aUser().userStatus(UserStatus.UNVERIFIED).authorizationRole(AuthorizationRole.CANDIDATE).build();

        final String newEmail = "leojardim@gmail.com";

        final User updatedUser = user.withEmail(newEmail);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(newEmail, updatedUser.getEmail());
    }

    @Test
    void shouldReturnUpdatedUser_whenCallWithDescription() {
        final User user = UserTestBuilder.aUser().userStatus(UserStatus.UNVERIFIED).authorizationRole(AuthorizationRole.CANDIDATE).build();

        final String newDescription = "My description about me";

        final User updatedUser = user.withDescription(newDescription);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(newDescription, updatedUser.getDescription());
    }

    @Test
    void shouldReturnUpdatedUser_whenCallWithCity() {
        final User user = UserTestBuilder.aUser().userStatus(UserStatus.UNVERIFIED).authorizationRole(AuthorizationRole.CANDIDATE).build();

        final String newCity = "São Paulo";

        final User updatedUser = user.withCity(newCity);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(newCity, updatedUser.getCity());
    }

    @Test
    void shouldReturnUpdatedUser_whenCallWithState() {
        final User user = UserTestBuilder.aUser().userStatus(UserStatus.UNVERIFIED).authorizationRole(AuthorizationRole.CANDIDATE).build();

        final String newState = "São Paulo";

        final User updatedUser = user.withState(newState);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(newState, updatedUser.getState());
    }

    @Test
    void shouldReturnUpdatedUser_whenCallWithCountry() {
        final User user = UserTestBuilder.aUser().userStatus(UserStatus.UNVERIFIED).authorizationRole(AuthorizationRole.CANDIDATE).build();

        final String newCountry = "Brazil";

        final User updatedUser = user.withCountry(newCountry);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(newCountry, updatedUser.getCountry());
    }

    @Test
    void shouldReturnUpdatedUser_whenCallWithUserStatus() {
        final User user = UserTestBuilder.aUser().userStatus(UserStatus.UNVERIFIED).authorizationRole(AuthorizationRole.CANDIDATE).build();

        final UserStatus newUserStatus = UserStatus.VERIFIED;

        final User updatedUser = user.withUserStatus(newUserStatus);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(newUserStatus, updatedUser.getUserStatus());
    }

    @Test
    void shouldReturnUpdatedUser_whenCallWithPassword() {
        final User user = UserTestBuilder.aUser().userStatus(UserStatus.UNVERIFIED).authorizationRole(AuthorizationRole.CANDIDATE).build();

        final String newPassword = "123456";

        final User updatedUser = user.withPassword(newPassword);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(newPassword, updatedUser.getPassword());
    }

    @Test
    void shouldReturnFormattedUser_whenCallToString() {
        final User user = UserTestBuilder.aUser().userStatus(UserStatus.UNVERIFIED).authorizationRole(AuthorizationRole.CANDIDATE).build();

        final String expectedToString = "User{" +
                "id='" + user.getId() + '\'' +
                ", name='" + user.getName() + '\'' +
                ", email='" + user.getEmail() + '\'' +
                ", description='" + user.getDescription() + '\'' +
                ", password='" + user.getPassword() + '\'' +
                ", userStatus=" + user.getUserStatus() +
                ", authorizationRole=" + user.getAuthorizationRole() +
                ", city='" + user.getCity() + '\'' +
                ", state='" + user.getState() + '\'' +
                ", country='" + user.getCountry() + '\'' +
                ", createdAt=" + user.getCreatedAt() +
                '}';

        Assertions.assertEquals(expectedToString, user.toString());
    }
}
