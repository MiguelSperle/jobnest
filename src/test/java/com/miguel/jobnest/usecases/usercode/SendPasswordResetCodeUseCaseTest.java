package com.miguel.jobnest.usecases.usercode;

import com.miguel.jobnest.application.abstractions.producer.MessageProducer;
import com.miguel.jobnest.application.abstractions.providers.CodeProvider;
import com.miguel.jobnest.application.abstractions.repositories.UserCodeRepository;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.usecases.usercode.DefaultSendPasswordResetCodeUseCase;
import com.miguel.jobnest.application.usecases.usercode.inputs.SendPasswordResetCodeUseCaseInput;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.entities.UserCode;
import com.miguel.jobnest.domain.enums.AuthorizationRole;
import com.miguel.jobnest.domain.enums.UserCodeType;
import com.miguel.jobnest.domain.enums.UserStatus;
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
public class SendPasswordResetCodeUseCaseTest {
    @InjectMocks
    private DefaultSendPasswordResetCodeUseCase useCase;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserCodeRepository userCodeRepository;

    @Mock
    private CodeProvider codeProvider;

    @Mock
    private MessageProducer messageProducer;

    @Test
    void shouldSendPasswordResetCode() {
        final User user = UserBuilderTest.build(UserStatus.UNVERIFIED, AuthorizationRole.CANDIDATE);
        final UserCode userCode = UserCodeBuilderTest.build(UserCodeType.PASSWORD_RESET, TimeUtils.now().plusMinutes(15));

        final SendPasswordResetCodeUseCaseInput input = SendPasswordResetCodeUseCaseInput.with(user.getEmail());

        Mockito.when(this.userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(user));

        Mockito.when(this.userCodeRepository.findByUserIdAndCodeType(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());

        Mockito.when(this.codeProvider.generateCode(Mockito.anyInt(), Mockito.any())).thenReturn(userCode.getCode());

        Mockito.when(this.userCodeRepository.save(Mockito.any())).thenReturn(userCode);

        Mockito.doNothing().when(this.messageProducer).publish(Mockito.any(), Mockito.any(), Mockito.any());

        this.useCase.execute(input);

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).findByUserIdAndCodeType(Mockito.any(), Mockito.any());
        Mockito.verify(this.codeProvider, Mockito.times(1)).generateCode(Mockito.anyInt(), Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.messageProducer, Mockito.times(1)).publish(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    void shouldReplacePasswordResetCodeAndSend_whenUserAlreadyHasOne() {
        final User user = UserBuilderTest.build(UserStatus.UNVERIFIED, AuthorizationRole.CANDIDATE);
        final UserCode userCode = UserCodeBuilderTest.build(UserCodeType.PASSWORD_RESET, TimeUtils.now().plusMinutes(15));

        final SendPasswordResetCodeUseCaseInput input = SendPasswordResetCodeUseCaseInput.with(user.getEmail());

        Mockito.when(this.userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(user));

        Mockito.when(this.userCodeRepository.findByUserIdAndCodeType(Mockito.any(), Mockito.any())).thenReturn(Optional.of(userCode));

        Mockito.doNothing().when(this.userCodeRepository).deleteById(Mockito.any());

        Mockito.when(this.codeProvider.generateCode(Mockito.anyInt(), Mockito.any())).thenReturn(userCode.getCode());

        Mockito.when(this.userCodeRepository.save(Mockito.any())).thenReturn(userCode);

        Mockito.doNothing().when(this.messageProducer).publish(Mockito.any(), Mockito.any(), Mockito.any());

        this.useCase.execute(input);

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).findByUserIdAndCodeType(Mockito.any(), Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).deleteById(Mockito.any());
        Mockito.verify(this.codeProvider, Mockito.times(1)).generateCode(Mockito.anyInt(), Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(this.messageProducer, Mockito.times(1)).publish(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    void shouldThrowNotFoundException_whenUserDoesNotExist() {
        final String email = "jonesTucky@gmail.com";

        final SendPasswordResetCodeUseCaseInput input = SendPasswordResetCodeUseCaseInput.with(email);

        Mockito.when(this.userRepository.findByEmail(Mockito.any())).thenReturn(Optional.empty());

        final NotFoundException ex = Assertions.assertThrows(NotFoundException.class, () ->
                this.useCase.execute(input)
        );

        final String expectedErrorMessage = "User not found";

        Assertions.assertInstanceOf(NotFoundException.class, ex);
        Assertions.assertEquals(expectedErrorMessage, ex.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(Mockito.any());
    }
}
