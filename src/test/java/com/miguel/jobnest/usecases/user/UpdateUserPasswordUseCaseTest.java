package com.miguel.jobnest.usecases.user;

import com.miguel.jobnest.application.abstractions.providers.PasswordEncryptionProvider;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.services.SecurityService;
import com.miguel.jobnest.application.usecases.user.DefaultUpdateUserPasswordUseCase;
import com.miguel.jobnest.application.usecases.user.inputs.UpdateUserPasswordUseCaseInput;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.enums.AuthorizationRole;
import com.miguel.jobnest.domain.enums.UserStatus;
import com.miguel.jobnest.domain.exceptions.DomainException;
import com.miguel.jobnest.domain.exceptions.NotFoundException;
import com.miguel.jobnest.utils.UserBuilderTest;
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
    private PasswordEncryptionProvider passwordEncryptionProvider;

    @Mock
    private SecurityService securityService;

    @Test
    void shouldUpdateUserPassword() {
        final User user = UserBuilderTest.build(UserStatus.VERIFIED, AuthorizationRole.CANDIDATE);

        final UpdateUserPasswordUseCaseInput input = UpdateUserPasswordUseCaseInput.with(
                user.getPassword(),
                "123456A"
        );

        Mockito.when(this.securityService.getPrincipal()).thenReturn(user.getId());
        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(this.passwordEncryptionProvider.matches(Mockito.any(), Mockito.any())).thenReturn(true);
        Mockito.when(this.passwordEncryptionProvider.encode(Mockito.any())).thenReturn(input.password());
        Mockito.when(this.userRepository.save(Mockito.any())).thenAnswer(returnsFirstArg());

        this.useCase.execute(input);

        Mockito.verify(this.securityService, Mockito.times(1)).getPrincipal();
        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.passwordEncryptionProvider, Mockito.times(1)).matches(Mockito.any(), Mockito.any());
        Mockito.verify(this.passwordEncryptionProvider, Mockito.times(1)).encode(Mockito.any());
        Mockito.verify(this.userRepository, Mockito.times(1)).save(Mockito.argThat(userSaved ->
                Objects.equals(userSaved.getPassword(), input.password())
        ));
    }

    @Test
    void shouldThrowNotFoundException_whenUserDoesNotExist() {
        final UpdateUserPasswordUseCaseInput input = UpdateUserPasswordUseCaseInput.with(
                "1234BC",
                "123456A"
        );

        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final NotFoundException ex = Assertions.assertThrows(NotFoundException.class, () ->
                this.useCase.execute(input)
        );

        final String expectedErrorMessage = "User not found";

        Assertions.assertInstanceOf(NotFoundException.class, ex);
        Assertions.assertEquals(expectedErrorMessage, ex.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    void shouldThrowDomainException_whenCurrentPasswordIsInvalid() {
        final User user = UserBuilderTest.build(UserStatus.VERIFIED, AuthorizationRole.CANDIDATE);

        final UpdateUserPasswordUseCaseInput input = UpdateUserPasswordUseCaseInput.with(
                "1234BC",
                "123456A"
        );

        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(this.passwordEncryptionProvider.matches(Mockito.any(), Mockito.any())).thenReturn(false);

        final DomainException ex = Assertions.assertThrows(DomainException.class, () ->
                this.useCase.execute(input)
        );

        final String expectedErrorMessage = "Invalid current password";

        Assertions.assertInstanceOf(DomainException.class, ex);
        Assertions.assertEquals(expectedErrorMessage, ex.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.passwordEncryptionProvider, Mockito.times(1)).matches(Mockito.any(), Mockito.any());
    }
}
