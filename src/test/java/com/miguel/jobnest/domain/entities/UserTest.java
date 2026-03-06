package com.miguel.jobnest.domain.entities;

import com.miguel.jobnest.domain.builders.UserBuilder;
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
        Assertions.assertNotNull(newUser.getUserStatus());
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
        final User user = UserBuilder.user().name("Some name").build();

        final String newName = "New default name";

        final User updatedUser = user.withName(newName);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(newName, updatedUser.getName());
    }

    @Test
    void shouldReturnUpdatedUser_whenCallWithEmail() {
        final User user = UserBuilder.user().email("somemail@gmail.com").build();

        final String newEmail = "newdefault@gmail.com";

        final User updatedUser = user.withEmail(newEmail);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(newEmail, updatedUser.getEmail());
    }

    @Test
    void shouldReturnUpdatedUser_whenCallWithDescription() {
        final User user = UserBuilder.user().description("Some description").build();

        final String newDescription = "New default description";

        final User updatedUser = user.withDescription(newDescription);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(newDescription, updatedUser.getDescription());
    }

    @Test
    void shouldReturnUpdatedUser_whenCallWithCity() {
        final User user = UserBuilder.user().city("Some city").build();

        final String newCity = "New default city";

        final User updatedUser = user.withCity(newCity);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(newCity, updatedUser.getCity());
    }

    @Test
    void shouldReturnUpdatedUser_whenCallWithState() {
        final User user = UserBuilder.user().state("Some state").build();

        final String newState = "New default state";

        final User updatedUser = user.withState(newState);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(newState, updatedUser.getState());
    }

    @Test
    void shouldReturnUpdatedUser_whenCallWithCountry() {
        final User user = UserBuilder.user().country("Some country").build();

        final String newCountry = "New default country";

        final User updatedUser = user.withCountry(newCountry);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(newCountry, updatedUser.getCountry());
    }

    @Test
    void shouldReturnUpdatedUser_whenCallWithUserStatus() {
        final User user = UserBuilder.user().userStatus(UserStatus.UNVERIFIED).build();

        final UserStatus newUserStatus = UserStatus.VERIFIED;

        final User updatedUser = user.withUserStatus(newUserStatus);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(newUserStatus, updatedUser.getUserStatus());
    }

    @Test
    void shouldReturnUpdatedUser_whenCallWithPassword() {
        final User user = UserBuilder.user().password("123SBC").build();

        final String newPassword = "123456";

        final User updatedUser = user.withPassword(newPassword);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(newPassword, updatedUser.getPassword());
    }

    @Test
    void shouldReturnFormattedUser_whenCallToString() {
        final User user = UserBuilder.user()
                .id(IdentifierUtils.generateNewId())
                .name("Name")
                .email("example@gmail.com")
                .description("Description")
                .password("123456")
                .userStatus(UserStatus.UNVERIFIED)
                .authorizationRole(AuthorizationRole.CANDIDATE)
                .city("City")
                .country("Country")
                .createdAt(TimeUtils.now())
                .build();

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

        final String toStringResult = user.toString();

        Assertions.assertNotNull(toStringResult);
        Assertions.assertEquals(expectedToString, toStringResult);
    }
}
