package com.miguel.jobnest.application.usecases.user;

import com.miguel.jobnest.application.abstractions.providers.PasswordEncryption;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.services.SecurityService;
import com.miguel.jobnest.application.usecases.user.inputs.UpdateUserPasswordUseCaseInput;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.enums.AuthorizationRole;
import com.miguel.jobnest.domain.enums.UserStatus;
import com.miguel.jobnest.domain.exceptions.DomainException;
import com.miguel.jobnest.domain.exceptions.NotFoundException;
import com.miguel.jobnest.application.utils.UserTestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

import java.util.Objects;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UpdateUserPasswordUseCaseTest {
    @InjectMocks
    private DefaultUpdateUserPasswordUseCase useCase;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncryption passwordEncryption;

    @Mock
    private SecurityService securityService;

    @Test
    void shouldUpdateUserPassword() {
        final User user = UserTestBuilder.aUser().userStatus(UserStatus.VERIFIED).authorizationRole(AuthorizationRole.CANDIDATE).build();
        final String password = "123456A";

        final UpdateUserPasswordUseCaseInput input = UpdateUserPasswordUseCaseInput.with(
                user.getPassword(),
                password
        );

        Mockito.when(this.securityService.getPrincipal()).thenReturn(user.getId());
        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(this.passwordEncryption.matches(Mockito.any(), Mockito.any())).thenReturn(true);
        Mockito.when(this.passwordEncryption.encode(Mockito.any())).thenReturn(input.password());
        Mockito.when(this.userRepository.save(Mockito.any())).thenAnswer(returnsFirstArg());

        this.useCase.execute(input);

        Mockito.verify(this.securityService, Mockito.times(1)).getPrincipal();
        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.passwordEncryption, Mockito.times(1)).matches(Mockito.any(), Mockito.any());
        Mockito.verify(this.passwordEncryption, Mockito.times(1)).encode(Mockito.any());
        Mockito.verify(this.userRepository, Mockito.times(1)).save(Mockito.argThat(userSaved ->
                Objects.equals(userSaved.getPassword(), input.password())
        ));
    }

    @Test
    void shouldThrowNotFoundException_whenUserDoesNotExist() {
        final String currentPassword = "1234BC";
        final String password = "123456A";

        final UpdateUserPasswordUseCaseInput input = UpdateUserPasswordUseCaseInput.with(
                currentPassword,
                password
        );

        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final var ex = Assertions.assertThrows(NotFoundException.class, () ->
                this.useCase.execute(input)
        );

        final String expectedErrorMessage = "User not found";

        Assertions.assertEquals(expectedErrorMessage, ex.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    void shouldThrowDomainException_whenCurrentPasswordIsInvalid() {
        final User user = UserTestBuilder.aUser().userStatus(UserStatus.VERIFIED).authorizationRole(AuthorizationRole.CANDIDATE).build();
        final String currentPassword = "1234BC";
        final String password = "123456A";

        final UpdateUserPasswordUseCaseInput input = UpdateUserPasswordUseCaseInput.with(
                currentPassword,
                password
        );

        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(this.passwordEncryption.matches(Mockito.any(), Mockito.any())).thenReturn(false);

        final var ex = Assertions.assertThrows(DomainException.class, () ->
                this.useCase.execute(input)
        );

        final String expectedErrorMessage = "Invalid current password";
        final int expectedStatusCode = 422;

        Assertions.assertEquals(expectedErrorMessage, ex.getMessage());
        Assertions.assertEquals(expectedStatusCode, ex.getStatusCode());

        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.passwordEncryption, Mockito.times(1)).matches(Mockito.any(), Mockito.any());
    }
}
