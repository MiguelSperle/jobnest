package com.miguel.jobnest.application.usecases.user;

import com.miguel.jobnest.application.abstractions.repositories.UserCodeRepository;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.wrapper.TransactionExecutor;
import com.miguel.jobnest.application.usecases.user.inputs.UpdateUserToVerifiedUseCaseInput;
import com.miguel.jobnest.domain.builders.UserBuilder;
import com.miguel.jobnest.domain.builders.UserCodeBuilder;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.entities.UserCode;
import com.miguel.jobnest.domain.enums.UserStatus;
import com.miguel.jobnest.domain.exceptions.DomainException;
import com.miguel.jobnest.domain.exceptions.NotFoundException;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import com.miguel.jobnest.domain.utils.TimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

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
    void shouldUpdateUserToVerified_whenCallExecute() {
        final User user = UserBuilder.user().id(IdentifierUtils.generateNewId()).userStatus(UserStatus.UNVERIFIED).build();
        final UserCode userCode = UserCodeBuilder.userCode().code("123TETEL").userId(user.getId()).expiresIn(TimeUtils.now().plusMinutes(15)).build();

        final UpdateUserToVerifiedUseCaseInput input = UpdateUserToVerifiedUseCaseInput.with(userCode.getCode());

        Mockito.when(this.userCodeRepository.findByCodeAndCodeType(Mockito.any(), Mockito.any())).thenReturn(Optional.of(userCode));
        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));

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
        Mockito.verify(this.transactionExecutor, Mockito.times(1)).runTransaction(Mockito.any());
        Mockito.verify(this.userRepository, Mockito.times(1)).save(Mockito.argThat(userSaved ->
                userSaved.getUserStatus() == UserStatus.VERIFIED
        ));
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).deleteById(Mockito.any());
    }

    @Test
    void shouldThrowNotFoundException_whenCallExecute_becauseTheCodeDoesNotExist() {
        final String code = "ABC123C3";

        final UpdateUserToVerifiedUseCaseInput input = UpdateUserToVerifiedUseCaseInput.with(code);

        Mockito.when(this.userCodeRepository.findByCodeAndCodeType(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());

        final var ex = Assertions.assertThrows(NotFoundException.class, () ->
                this.useCase.execute(input)
        );

        final String expectedErrorMessage = "User code not found";

        Assertions.assertEquals(expectedErrorMessage, ex.getMessage());

        Mockito.verify(this.userCodeRepository, Mockito.times(1)).findByCodeAndCodeType(Mockito.any(), Mockito.any());
    }

    @Test
    void shouldThrowDomainException_whenCallExecute_becauseTheCodeIsExpired() {
        final UserCode userCode = UserCodeBuilder.userCode().code("TATU12345").expiresIn(TimeUtils.now().minusDays(1)).build();

        final UpdateUserToVerifiedUseCaseInput input = UpdateUserToVerifiedUseCaseInput.with(userCode.getCode());

        Mockito.when(this.userCodeRepository.findByCodeAndCodeType(Mockito.any(), Mockito.any())).thenReturn(Optional.of(userCode));
        Mockito.doNothing().when(this.userCodeRepository).deleteById(Mockito.any());

        final var ex = Assertions.assertThrows(DomainException.class, () ->
                this.useCase.execute(input)
        );

        final String expectedErrorMessage = "User code has expired";
        final int expectedStatusCode = 410;

        Assertions.assertEquals(expectedErrorMessage, ex.getMessage());
        Assertions.assertEquals(expectedStatusCode, ex.getStatusCode());

        Mockito.verify(this.userCodeRepository, Mockito.times(1)).findByCodeAndCodeType(Mockito.any(), Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).deleteById(Mockito.any());
    }

    @Test
    void shouldThrowNotFoundException_whenCallExecute_becauseUserDoesNotExist() {
        final UserCode userCode = UserCodeBuilder.userCode().code("EYE142TA").expiresIn(TimeUtils.now().plusMinutes(15)).build();

        final UpdateUserToVerifiedUseCaseInput input = UpdateUserToVerifiedUseCaseInput.with(userCode.getCode());

        Mockito.when(this.userCodeRepository.findByCodeAndCodeType(Mockito.any(), Mockito.any())).thenReturn(Optional.of(userCode));
        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final var ex = Assertions.assertThrows(NotFoundException.class, () ->
                this.useCase.execute(input)
        );

        final String expectedErrorMessage = "User not found";

        Assertions.assertEquals(expectedErrorMessage, ex.getMessage());

        Mockito.verify(this.userCodeRepository, Mockito.times(1)).findByCodeAndCodeType(Mockito.any(), Mockito.any());
        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
    }
}
