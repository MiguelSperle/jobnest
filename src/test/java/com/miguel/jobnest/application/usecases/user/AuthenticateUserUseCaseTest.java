package com.miguel.jobnest.application.usecases.user;

import com.miguel.jobnest.application.abstractions.providers.PasswordEncryption;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.services.JwtService;
import com.miguel.jobnest.application.usecases.user.inputs.AuthenticateUserUseCaseInput;
import com.miguel.jobnest.application.usecases.user.outputs.AuthenticateUserUseCaseOutput;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.enums.AuthorizationRole;
import com.miguel.jobnest.domain.enums.UserStatus;
import com.miguel.jobnest.domain.exceptions.DomainException;
import com.miguel.jobnest.application.builders.UserTestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AuthenticateUserUseCaseTest {
    @InjectMocks
    private DefaultAuthenticateUserUseCase useCase;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncryption passwordEncryption;

    @Mock
    private JwtService jwtService;

    @Test
    void shouldAuthenticateUser_whenCallExecute() {
        final User user = UserTestBuilder.aUser().userStatus(UserStatus.VERIFIED).authorizationRole(AuthorizationRole.CANDIDATE).build();
        final String jwt = "json-web-token";

        final AuthenticateUserUseCaseInput input = AuthenticateUserUseCaseInput.with(
                user.getEmail(),
                user.getPassword()
        );

        Mockito.when(this.userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(this.passwordEncryption.matches(Mockito.any(), Mockito.any())).thenReturn(true);
        Mockito.when(this.jwtService.generateJwt(Mockito.any(), Mockito.any())).thenReturn(jwt);

        final AuthenticateUserUseCaseOutput output = this.useCase.execute(input);

        Assertions.assertNotNull(output);
        Assertions.assertNotNull(output.accessToken());
        Assertions.assertEquals(jwt, output.accessToken());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(Mockito.any());
        Mockito.verify(this.passwordEncryption, Mockito.times(1)).matches(Mockito.any(), Mockito.any());
        Mockito.verify(this.jwtService, Mockito.times(1)).generateJwt(Mockito.any(), Mockito.any());
    }

    @Test
    void shouldThrowDomainException_whenCallExecute_becauseUserDoesNotExist() {
        final String email = "alexhand1898@gmail.com";
        final String password = "1234BC";

        final AuthenticateUserUseCaseInput input = AuthenticateUserUseCaseInput.with(
                email,
                password
        );

        Mockito.when(this.userRepository.findByEmail(Mockito.any())).thenReturn(Optional.empty());

        final DomainException ex = Assertions.assertThrows(DomainException.class, () ->
                this.useCase.execute(input)
        );

        final String expectedErrorMessage = "Wrong credentials";
        final int expectedStatusCode = 401;

        Assertions.assertInstanceOf(DomainException.class, ex);
        Assertions.assertEquals(expectedErrorMessage, ex.getMessage());
        Assertions.assertEquals(expectedStatusCode, ex.getStatusCode());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(Mockito.any());
    }

    @Test
    void shouldThrowDomainException_whenCallExecute_becausePasswordIsInvalid() {
        final User user = UserTestBuilder.aUser().userStatus(UserStatus.VERIFIED).authorizationRole(AuthorizationRole.CANDIDATE).build();
        final String password = "1234BC";

        final AuthenticateUserUseCaseInput input = AuthenticateUserUseCaseInput.with(
                user.getEmail(),
                password
        );

        Mockito.when(this.userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(this.passwordEncryption.matches(Mockito.any(), Mockito.any())).thenReturn(false);

        final var ex = Assertions.assertThrows(DomainException.class, () ->
                this.useCase.execute(input)
        );

        final String expectedErrorMessage = "Wrong credentials";
        final int expectedStatusCode = 401;

        Assertions.assertEquals(expectedErrorMessage, ex.getMessage());
        Assertions.assertEquals(expectedStatusCode, ex.getStatusCode());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(Mockito.any());
        Mockito.verify(this.passwordEncryption, Mockito.times(1)).matches(Mockito.any(), Mockito.any());
    }

    @Test
    void shouldThrowDomainException_whenCallExecute_becauseUserIsNotVerified() {
        final User user = UserTestBuilder.aUser().userStatus(UserStatus.UNVERIFIED).authorizationRole(AuthorizationRole.CANDIDATE).build();

        final AuthenticateUserUseCaseInput input = AuthenticateUserUseCaseInput.with(
                user.getEmail(),
                user.getPassword()
        );

        Mockito.when(this.userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(this.passwordEncryption.matches(Mockito.any(), Mockito.any())).thenReturn(true);

        final DomainException ex = Assertions.assertThrows(DomainException.class, () ->
                this.useCase.execute(input)
        );

        final String expectedErrorMessage = "User not verified";
        final int expectedStatusCode = 403;

        Assertions.assertInstanceOf(DomainException.class, ex);
        Assertions.assertEquals(expectedErrorMessage, ex.getMessage());
        Assertions.assertEquals(expectedStatusCode, ex.getStatusCode());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(Mockito.any());
        Mockito.verify(this.passwordEncryption, Mockito.times(1)).matches(Mockito.any(), Mockito.any());
    }

    @Test
    void shouldThrowDomainException_whenCallExecute_becauseUserIsDeleted() {
        final User user = UserTestBuilder.aUser().userStatus(UserStatus.DELETED).authorizationRole(AuthorizationRole.CANDIDATE).build();

        final AuthenticateUserUseCaseInput input = AuthenticateUserUseCaseInput.with(
                user.getEmail(),
                user.getPassword()
        );

        Mockito.when(this.userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(this.passwordEncryption.matches(Mockito.any(), Mockito.any())).thenReturn(true);

        final DomainException ex = Assertions.assertThrows(DomainException.class, () ->
                this.useCase.execute(input)
        );

        final String expectedErrorMessage = "User has been deleted";
        final int expectedStatusCode = 403;

        Assertions.assertInstanceOf(DomainException.class, ex);
        Assertions.assertEquals(expectedErrorMessage, ex.getMessage());
        Assertions.assertEquals(expectedStatusCode, ex.getStatusCode());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(Mockito.any());
        Mockito.verify(this.passwordEncryption, Mockito.times(1)).matches(Mockito.any(), Mockito.any());
    }
}
