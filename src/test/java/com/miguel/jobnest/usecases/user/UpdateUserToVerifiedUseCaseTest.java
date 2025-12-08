package com.miguel.jobnest.usecases.user;

import com.miguel.jobnest.application.abstractions.repositories.UserCodeRepository;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.wrapper.TransactionExecutor;
import com.miguel.jobnest.application.usecases.user.DefaultUpdateUserToVerifiedUseCase;
import com.miguel.jobnest.application.usecases.user.inputs.UpdateUserToVerifiedUseCaseInput;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.entities.UserCode;
import com.miguel.jobnest.domain.enums.AuthorizationRole;
import com.miguel.jobnest.domain.enums.UserCodeType;
import com.miguel.jobnest.domain.enums.UserStatus;
import com.miguel.jobnest.domain.exceptions.DomainException;
import com.miguel.jobnest.domain.exceptions.NotFoundException;
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

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UpdateUserToVerifiedUseCaseTest {
    @InjectMocks
    private DefaultUpdateUserToVerifiedUseCase useCase;

    @Mock
    private UserCodeRepository userCodeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionExecutor transactionExecutor;

    @Test
    void shouldUpdateUserToVerified() {
        final UserCode userCode = UserCodeBuilderTest.build(UserCodeType.USER_VERIFICATION, TimeUtils.now().plusMinutes(15));
        final User user = UserBuilderTest.build(UserStatus.UNVERIFIED, AuthorizationRole.CANDIDATE);

        final UpdateUserToVerifiedUseCaseInput input = UpdateUserToVerifiedUseCaseInput.with(userCode.getCode());

        Mockito.when(this.userCodeRepository.findByCodeAndCodeType(Mockito.any(), Mockito.any())).thenReturn(Optional.of(userCode));
        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));

        final User updatedUser = user.withUserStatus(UserStatus.VERIFIED);

        Mockito.doAnswer(invocationOnMock -> {
            Runnable runnable = invocationOnMock.getArgument(0);
            runnable.run();
            return runnable;
        }).when(this.transactionExecutor).runTransaction(Mockito.any());

        Mockito.when(this.userRepository.save(Mockito.any())).thenReturn(updatedUser);
        Mockito.doNothing().when(this.userCodeRepository).deleteById(Mockito.any());

        this.useCase.execute(input);

        Mockito.verify(this.userCodeRepository, Mockito.times(1)).findByCodeAndCodeType(Mockito.any(), Mockito.any());
        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.transactionExecutor, Mockito.times(1)).runTransaction(Mockito.any());
        Mockito.verify(this.userRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).deleteById(Mockito.any());
    }

    @Test
    void shouldThrowNotFoundException_whenTheCodeDoesNotExist() {
        final UpdateUserToVerifiedUseCaseInput input = UpdateUserToVerifiedUseCaseInput.with("ABC123C3");

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
        final UserCode userCode = UserCodeBuilderTest.build(UserCodeType.USER_VERIFICATION, TimeUtils.now().minusDays(1));

        final UpdateUserToVerifiedUseCaseInput input = UpdateUserToVerifiedUseCaseInput.with(userCode.getCode());

        Mockito.when(this.userCodeRepository.findByCodeAndCodeType(Mockito.any(), Mockito.any())).thenReturn(Optional.of(userCode));
        Mockito.doNothing().when(this.userCodeRepository).deleteById(Mockito.any());

        final DomainException ex = Assertions.assertThrows(DomainException.class, () ->
                this.useCase.execute(input)
        );

        final String expectedErrorMessage = "User code has expired";

        Assertions.assertInstanceOf(DomainException.class, ex);
        Assertions.assertEquals(expectedErrorMessage, ex.getMessage());

        Mockito.verify(this.userCodeRepository, Mockito.times(1)).findByCodeAndCodeType(Mockito.any(), Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).deleteById(Mockito.any());
    }

    @Test
    void shouldThrowNotFoundException_whenUserDoesNotExist() {
        final UserCode userCode = UserCodeBuilderTest.build(UserCodeType.USER_VERIFICATION, TimeUtils.now().plusMinutes(15));

        final UpdateUserToVerifiedUseCaseInput input = UpdateUserToVerifiedUseCaseInput.with(userCode.getCode());

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
