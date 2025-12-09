package com.miguel.jobnest.usecases.user;

import com.miguel.jobnest.application.abstractions.providers.PasswordEncryptionProvider;
import com.miguel.jobnest.application.abstractions.repositories.UserCodeRepository;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.wrapper.TransactionExecutor;
import com.miguel.jobnest.application.usecases.user.DefaultResetUserPasswordUseCase;
import com.miguel.jobnest.application.usecases.user.inputs.ResetUserPasswordUseCaseInput;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.entities.UserCode;
import com.miguel.jobnest.domain.enums.AuthorizationRole;
import com.miguel.jobnest.domain.enums.UserCodeType;
import com.miguel.jobnest.domain.enums.UserStatus;
import com.miguel.jobnest.domain.exceptions.DomainException;
import com.miguel.jobnest.domain.exceptions.NotFoundException;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;
import com.miguel.jobnest.utils.UserBuilderTest;
import com.miguel.jobnest.utils.UserCodeBuilderTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

@ExtendWith(MockitoExtension.class)
public class ResetUserPasswordUseCaseTest {
    @InjectMocks
    private DefaultResetUserPasswordUseCase useCase;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserCodeRepository userCodeRepository;

    @Mock
    private PasswordEncryptionProvider passwordEncryptionProvider;

    @Mock
    private TransactionExecutor transactionExecutor;

    @Test
    void shouldResetUserPassword() {
        final User user = UserBuilderTest.build(UserStatus.VERIFIED, AuthorizationRole.CANDIDATE);
        final UserCode userCode = UserCodeBuilderTest.build(UserCodeType.PASSWORD_RESET, TimeUtils.now().plusMinutes(15), user.getId());

        final ResetUserPasswordUseCaseInput input = ResetUserPasswordUseCaseInput.with(
                userCode.getCode(),
                "123456A"
        );

        Mockito.when(this.userCodeRepository.findByCodeAndCodeType(Mockito.any(), Mockito.any())).thenReturn(Optional.of(userCode));
        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(this.passwordEncryptionProvider.encode(Mockito.any())).thenReturn(input.password());

        Mockito.doAnswer(invocationOnMock -> {
            Runnable runnable = invocationOnMock.getArgument(0);
            runnable.run();
            return runnable;
        }).when(this.transactionExecutor).runTransaction(Mockito.any());

        Mockito.when(this.userRepository.save(Mockito.any())).thenAnswer(returnsFirstArg());
        Mockito.doNothing().when(this.userCodeRepository).deleteById(Mockito.any());

        this.useCase.execute(input);

        Mockito.verify(this.userCodeRepository, Mockito.times(1)).findByCodeAndCodeType(Mockito.any(), Mockito.any());
        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.passwordEncryptionProvider, Mockito.times(1)).encode(Mockito.any());
        Mockito.verify(this.transactionExecutor, Mockito.times(1)).runTransaction(Mockito.any());
        Mockito.verify(this.userRepository, Mockito.times(1)).save(Mockito.argThat(userSaved ->
                Objects.equals(userSaved.getPassword(), input.password())
        ));
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).deleteById(Mockito.any());
    }

    @Test
    void shouldThrowNotFoundException_whenTheCodeDoesNotExist() {
        final ResetUserPasswordUseCaseInput input = ResetUserPasswordUseCaseInput.with(
                "1234BC",
                "123456A"
        );

        Mockito.when(this.userCodeRepository.findByCodeAndCodeType(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());

        final NotFoundException ex = Assertions.assertThrows(NotFoundException.class, () ->
                this.useCase.execute(input)
        );

        final String expectedErrorMessage = "User code not found";

        Assertions.assertInstanceOf(NotFoundException.class, ex);
        Assertions.assertEquals(expectedErrorMessage, ex.getMessage());

        Mockito.verify(this.userCodeRepository, Mockito.times(1)).findByCodeAndCodeType(Mockito.any(), Mockito.any());
    }

    @Test
    void shouldThrowDomainException_whenTheCodeIsExpired() {
        final User user = UserBuilderTest.build(UserStatus.VERIFIED, AuthorizationRole.CANDIDATE);
        final UserCode userCode = UserCodeBuilderTest.build(UserCodeType.PASSWORD_RESET, TimeUtils.now().minusDays(1), user.getId());

        final ResetUserPasswordUseCaseInput input = ResetUserPasswordUseCaseInput.with(
                userCode.getCode(),
                "123456A"
        );

        Mockito.when(this.userCodeRepository.findByCodeAndCodeType(Mockito.any(), Mockito.any())).thenReturn(Optional.of(userCode));
        Mockito.doNothing().when(this.userCodeRepository).deleteById(Mockito.any());

        final DomainException ex = Assertions.assertThrows(DomainException.class, () ->
                this.useCase.execute(input)
        );

        final String expectedErrorMessage = "User code has expired";
        final int expectedStatusCode = 410;

        Assertions.assertInstanceOf(DomainException.class, ex);
        Assertions.assertEquals(expectedErrorMessage, ex.getMessage());
        Assertions.assertEquals(expectedStatusCode, ex.getStatusCode());

        Mockito.verify(this.userCodeRepository, Mockito.times(1)).findByCodeAndCodeType(Mockito.any(), Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).deleteById(Mockito.any());
    }

    @Test
    void shouldThrowNotFoundException_whenUserDoesNotExist() {
        final UserCode userCode = UserCodeBuilderTest.build(UserCodeType.PASSWORD_RESET, TimeUtils.now().plusMinutes(15), IdentifierUtils.generateUUID());

        final ResetUserPasswordUseCaseInput input = ResetUserPasswordUseCaseInput.with(
                "1234BC",
                "123456A"
        );

        Mockito.when(this.userCodeRepository.findByCodeAndCodeType(Mockito.any(), Mockito.any())).thenReturn(Optional.of(userCode));
        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final NotFoundException ex = Assertions.assertThrows(NotFoundException.class, () ->
                this.useCase.execute(input)
        );

        final String expectedErrorMessage = "User not found";

        Assertions.assertInstanceOf(NotFoundException.class, ex);
        Assertions.assertEquals(expectedErrorMessage, ex.getMessage());

        Mockito.verify(this.userCodeRepository, Mockito.times(1)).findByCodeAndCodeType(Mockito.any(), Mockito.any());
        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
    }
}
