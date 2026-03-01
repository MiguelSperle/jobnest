package com.miguel.jobnest.application.usecases.usercode;

import com.miguel.jobnest.application.abstractions.repositories.EventOutboxRepository;
import com.miguel.jobnest.application.abstractions.wrapper.TransactionExecutor;
import com.miguel.jobnest.application.abstractions.providers.CodeGenerator;
import com.miguel.jobnest.application.abstractions.repositories.UserCodeRepository;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.usecases.usercode.inputs.SendPasswordResetCodeUseCaseInput;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.entities.UserCode;
import com.miguel.jobnest.domain.enums.AuthorizationRole;
import com.miguel.jobnest.domain.enums.UserCodeType;
import com.miguel.jobnest.domain.enums.UserStatus;
import com.miguel.jobnest.domain.exceptions.NotFoundException;
import com.miguel.jobnest.domain.utils.TimeUtils;
import com.miguel.jobnest.application.builders.UserTestBuilder;
import com.miguel.jobnest.application.builders.UserCodeTestBuilder;
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
public class SendPasswordResetCodeUseCaseTest {
    @InjectMocks
    private DefaultSendPasswordResetCodeUseCase useCase;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserCodeRepository userCodeRepository;

    @Mock
    private CodeGenerator codeGenerator;

    @Mock
    private EventOutboxRepository eventOutboxRepository;

    @Mock
    private TransactionExecutor transactionExecutor;

    @Test
    void shouldSendPasswordResetCode_whenCallExecute() {
        final User user = UserTestBuilder.aUser().userStatus(UserStatus.UNVERIFIED).authorizationRole(AuthorizationRole.CANDIDATE).build();
        final String code = "123AB24J";

        final SendPasswordResetCodeUseCaseInput input = SendPasswordResetCodeUseCaseInput.with(user.getEmail());

        Mockito.when(this.userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(this.userCodeRepository.findByUserIdAndCodeType(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(this.codeGenerator.generateCode(Mockito.anyInt(), Mockito.any())).thenReturn(code);
        Mockito.doAnswer(invocationOnMock -> {
            Runnable runnable = invocationOnMock.getArgument(0);
            runnable.run();
            return runnable;
        }).when(this.transactionExecutor).runTransaction(Mockito.any());
        Mockito.when(this.userCodeRepository.save(Mockito.any())).thenAnswer(returnsFirstArg());
        Mockito.doNothing().when(this.eventOutboxRepository).save(Mockito.any(), Mockito.any(), Mockito.any());

        this.useCase.execute(input);

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).findByUserIdAndCodeType(Mockito.any(), Mockito.any());
        Mockito.verify(this.codeGenerator, Mockito.times(1)).generateCode(Mockito.anyInt(), Mockito.any());
        Mockito.verify(this.transactionExecutor, Mockito.times(1)).runTransaction(Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).save(Mockito.argThat(userCodeSaved ->
                Objects.nonNull(userCodeSaved.getId()) &&
                        Objects.equals(userCodeSaved.getUserId(), user.getId()) &&
                        Objects.equals(userCodeSaved.getCode(), code) &&
                        Objects.equals(userCodeSaved.getUserCodeType(), UserCodeType.PASSWORD_RESET) &&
                        userCodeSaved.getExpiresIn().isAfter(TimeUtils.now()) &&
                        Objects.nonNull(userCodeSaved.getCreatedAt())
        ));
        Mockito.verify(this.eventOutboxRepository, Mockito.times(1)).save(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    void shouldReplacePasswordResetCodeAndSend_whenCallExecute_becauseUserAlreadyHasOne() {
        final User user = UserTestBuilder.aUser().userStatus(UserStatus.UNVERIFIED).authorizationRole(AuthorizationRole.CANDIDATE).build();
        final UserCode userCode = UserCodeTestBuilder.aUserCode().userId(user.getId()).userCodeType(UserCodeType.PASSWORD_RESET).expiresIn(TimeUtils.now().plusMinutes(15)).build();
        final String code = "123AB24J";

        final SendPasswordResetCodeUseCaseInput input = SendPasswordResetCodeUseCaseInput.with(user.getEmail());

        Mockito.when(this.userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(this.userCodeRepository.findByUserIdAndCodeType(Mockito.any(), Mockito.any())).thenReturn(Optional.of(userCode));
        Mockito.doNothing().when(this.userCodeRepository).deleteById(Mockito.any());
        Mockito.when(this.codeGenerator.generateCode(Mockito.anyInt(), Mockito.any())).thenReturn(code);
        Mockito.doAnswer(invocationOnMock -> {
            Runnable runnable = invocationOnMock.getArgument(0);
            runnable.run();
            return runnable;
        }).when(this.transactionExecutor).runTransaction(Mockito.any());
        Mockito.when(this.userCodeRepository.save(Mockito.any())).thenAnswer(returnsFirstArg());
        Mockito.doNothing().when(this.eventOutboxRepository).save(Mockito.any(), Mockito.any(), Mockito.any());

        this.useCase.execute(input);

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).findByUserIdAndCodeType(Mockito.any(), Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).deleteById(Mockito.any());
        Mockito.verify(this.codeGenerator, Mockito.times(1)).generateCode(Mockito.anyInt(), Mockito.any());
        Mockito.verify(this.transactionExecutor, Mockito.times(1)).runTransaction(Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).save(Mockito.argThat(userCodeSaved ->
                Objects.nonNull(userCodeSaved.getId()) &&
                        Objects.equals(userCodeSaved.getUserId(), user.getId()) &&
                        Objects.equals(userCodeSaved.getCode(), code) &&
                        Objects.equals(userCodeSaved.getUserCodeType(), UserCodeType.PASSWORD_RESET) &&
                        userCodeSaved.getExpiresIn().isAfter(TimeUtils.now()) &&
                        Objects.nonNull(userCodeSaved.getCreatedAt())
        ));
        Mockito.verify(this.eventOutboxRepository, Mockito.times(1)).save(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    void shouldThrowNotFoundException_whenCallExecute_becauseUserDoesNotExist() {
        final String email = "jonestucky@gmail.com";

        final SendPasswordResetCodeUseCaseInput input = SendPasswordResetCodeUseCaseInput.with(email);

        Mockito.when(this.userRepository.findByEmail(Mockito.any())).thenReturn(Optional.empty());

        final var ex = Assertions.assertThrows(NotFoundException.class, () ->
                this.useCase.execute(input)
        );

        final String expectedErrorMessage = "User not found";

        Assertions.assertEquals(expectedErrorMessage, ex.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(Mockito.any());
    }
}
