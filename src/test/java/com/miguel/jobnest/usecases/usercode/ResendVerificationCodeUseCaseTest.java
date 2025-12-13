package com.miguel.jobnest.usecases.usercode;

import com.miguel.jobnest.application.abstractions.producer.MessageProducer;
import com.miguel.jobnest.application.abstractions.providers.CodeProvider;
import com.miguel.jobnest.application.abstractions.repositories.UserCodeRepository;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.usecases.usercode.DefaultResendVerificationCodeUseCase;
import com.miguel.jobnest.application.usecases.usercode.inputs.ResendVerificationCodeUseCaseInput;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.entities.UserCode;
import com.miguel.jobnest.domain.enums.AuthorizationRole;
import com.miguel.jobnest.domain.enums.UserCodeType;
import com.miguel.jobnest.domain.enums.UserStatus;
import com.miguel.jobnest.domain.exceptions.DomainException;
import com.miguel.jobnest.domain.exceptions.NotFoundException;
import com.miguel.jobnest.domain.utils.TimeUtils;
import com.miguel.jobnest.utils.UserTestBuilder;
import com.miguel.jobnest.utils.UserCodeTestBuilder;
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
public class ResendVerificationCodeUseCaseTest {
    @InjectMocks
    private DefaultResendVerificationCodeUseCase useCase;

    @Mock
    private UserCodeRepository userCodeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MessageProducer messageProducer;

    @Mock
    private CodeProvider codeProvider;

    @Test
    void shouldResendVerificationCode() {
        final User user = UserTestBuilder.aUser().userStatus(UserStatus.UNVERIFIED).authorizationRole(AuthorizationRole.CANDIDATE).build();
        final String code = "123AB24J";

        final ResendVerificationCodeUseCaseInput input = ResendVerificationCodeUseCaseInput.with(user.getEmail());

        Mockito.when(this.userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(this.userCodeRepository.findByUserIdAndCodeType(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(this.codeProvider.generateCode(Mockito.anyInt(), Mockito.any())).thenReturn(code);
        Mockito.when(this.userCodeRepository.save(Mockito.any())).thenAnswer(returnsFirstArg());
        Mockito.doNothing().when(this.messageProducer).publish(Mockito.any(), Mockito.any(), Mockito.any());

        this.useCase.execute(input);

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).findByUserIdAndCodeType(Mockito.any(), Mockito.any());
        Mockito.verify(this.codeProvider, Mockito.times(1)).generateCode(Mockito.anyInt(), Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).save(Mockito.argThat(userCodeSaved ->
                Objects.nonNull(userCodeSaved.getId()) &&
                        Objects.equals(userCodeSaved.getUserId(), user.getId()) &&
                        Objects.equals(userCodeSaved.getCode(), code) &&
                        Objects.equals(userCodeSaved.getUserCodeType(), UserCodeType.USER_VERIFICATION) &&
                        userCodeSaved.getExpiresIn().isAfter(TimeUtils.now()) &&
                        Objects.nonNull(userCodeSaved.getCreatedAt())
        ));
        Mockito.verify(this.messageProducer, Mockito.times(1)).publish(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    void shouldReplaceVerificationCodeAndSend_whenUserAlreadyHasOne() {
        final User user = UserTestBuilder.aUser().userStatus(UserStatus.UNVERIFIED).authorizationRole(AuthorizationRole.CANDIDATE).build();
        final UserCode userCode = UserCodeTestBuilder.aUserCode().userId(user.getId()).userCodeType(UserCodeType.USER_VERIFICATION).expiresIn(TimeUtils.now().plusMinutes(15)).build();
        final String code = "123AB24J";

        final ResendVerificationCodeUseCaseInput input = ResendVerificationCodeUseCaseInput.with(user.getEmail());

        Mockito.when(this.userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(this.userCodeRepository.findByUserIdAndCodeType(Mockito.any(), Mockito.any())).thenReturn(Optional.of(userCode));
        Mockito.doNothing().when(this.userCodeRepository).deleteById(Mockito.any());
        Mockito.when(this.codeProvider.generateCode(Mockito.anyInt(), Mockito.any())).thenReturn(code);
        Mockito.when(this.userCodeRepository.save(Mockito.any())).thenAnswer(returnsFirstArg());
        Mockito.doNothing().when(this.messageProducer).publish(Mockito.any(), Mockito.any(), Mockito.any());

        this.useCase.execute(input);

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).findByUserIdAndCodeType(Mockito.any(), Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).deleteById(Mockito.any());
        Mockito.verify(this.codeProvider, Mockito.times(1)).generateCode(Mockito.anyInt(), Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).save(Mockito.argThat(userCodeSaved ->
                Objects.nonNull(userCodeSaved.getId()) &&
                        Objects.equals(userCodeSaved.getUserId(), user.getId()) &&
                        Objects.equals(userCodeSaved.getCode(), code) &&
                        Objects.equals(userCodeSaved.getUserCodeType(), UserCodeType.USER_VERIFICATION) &&
                        userCodeSaved.getExpiresIn().isAfter(TimeUtils.now()) &&
                        Objects.nonNull(userCodeSaved.getCreatedAt())
        ));
        Mockito.verify(this.messageProducer, Mockito.times(1)).publish(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    void shouldThrowNotFoundException_whenUserDoesNotExist() {
        final String email = "jonestucky@gmail.com";

        final ResendVerificationCodeUseCaseInput input = ResendVerificationCodeUseCaseInput.with(email);

        Mockito.when(this.userRepository.findByEmail(Mockito.any())).thenReturn(Optional.empty());

        final var ex = Assertions.assertThrows(NotFoundException.class, () ->
                this.useCase.execute(input)
        );

        final String expectedErrorMessage = "User not found";

        Assertions.assertEquals(expectedErrorMessage, ex.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(Mockito.any());
    }

    @Test
    void shouldThrowDomainException_whenUserIsAlreadyVerified() {
        final User user = UserTestBuilder.aUser().userStatus(UserStatus.VERIFIED).authorizationRole(AuthorizationRole.CANDIDATE).build();

        final ResendVerificationCodeUseCaseInput input = ResendVerificationCodeUseCaseInput.with(user.getEmail());

        Mockito.when(this.userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(user));

        final var ex = Assertions.assertThrows(DomainException.class, () ->
                this.useCase.execute(input)
        );

        final String expectedErrorMessage = "User has already been verified";
        final int expectedStatusCode = 409;

        Assertions.assertEquals(expectedErrorMessage, ex.getMessage());
        Assertions.assertEquals(expectedStatusCode, ex.getStatusCode());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(Mockito.any());
    }

    @Test
    void shouldThrowDomainException_whenUserIsDeleted() {
        final User user = UserTestBuilder.aUser().userStatus(UserStatus.DELETED).authorizationRole(AuthorizationRole.CANDIDATE).build();

        final ResendVerificationCodeUseCaseInput input = ResendVerificationCodeUseCaseInput.with(user.getEmail());

        Mockito.when(this.userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(user));

        final var ex = Assertions.assertThrows(DomainException.class, () ->
                this.useCase.execute(input)
        );

        final String expectedErrorMessage = "User has already been deleted";
        final int expectedStatusCode = 409;

        Assertions.assertEquals(expectedErrorMessage, ex.getMessage());
        Assertions.assertEquals(expectedStatusCode, ex.getStatusCode());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(Mockito.any());
    }
}
