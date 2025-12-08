package com.miguel.jobnest.usecases.usercode;

import com.miguel.jobnest.application.abstractions.repositories.UserCodeRepository;
import com.miguel.jobnest.application.usecases.usercode.DefaultValidatePasswordResetCodeUseCase;
import com.miguel.jobnest.application.usecases.usercode.inputs.ValidatePasswordResetCodeUseCaseInput;
import com.miguel.jobnest.domain.entities.UserCode;
import com.miguel.jobnest.domain.enums.UserCodeType;
import com.miguel.jobnest.domain.exceptions.DomainException;
import com.miguel.jobnest.domain.exceptions.NotFoundException;
import com.miguel.jobnest.domain.utils.TimeUtils;
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
public class ValidatePasswordResetCodeUseCaseTest {
    @InjectMocks
    private DefaultValidatePasswordResetCodeUseCase validatePasswordResetCodeUseCase;

    @Mock
    private UserCodeRepository userCodeRepository;

    @Test
    void shouldValidatePasswordResetCode() {
        final UserCode userCode = UserCodeBuilderTest.build(UserCodeType.PASSWORD_RESET, TimeUtils.now().plusMinutes(15));

        final ValidatePasswordResetCodeUseCaseInput input = ValidatePasswordResetCodeUseCaseInput.with(userCode.getCode());

        Mockito.when(this.userCodeRepository.findByCodeAndCodeType(Mockito.any(), Mockito.any())).thenReturn(Optional.of(userCode));

        this.validatePasswordResetCodeUseCase.execute(input);

        Mockito.verify(this.userCodeRepository, Mockito.times(1)).findByCodeAndCodeType(Mockito.any(), Mockito.any());
    }

    @Test
    void shouldThrowNotFoundException_whenTheCodeDoesNotExist() {
        final String code = "ABC123C3";

        final ValidatePasswordResetCodeUseCaseInput input = ValidatePasswordResetCodeUseCaseInput.with(code);

        Mockito.when(this.userCodeRepository.findByCodeAndCodeType(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());

        final NotFoundException ex = Assertions.assertThrows(NotFoundException.class, () ->
                this.validatePasswordResetCodeUseCase.execute(input)
        );

        final String expectedErrorMessage = "User code not found";

        Assertions.assertInstanceOf(NotFoundException.class, ex);
        Assertions.assertEquals(expectedErrorMessage, ex.getMessage());

        Mockito.verify(this.userCodeRepository, Mockito.times(1)).findByCodeAndCodeType(Mockito.any(), Mockito.any());
    }

    @Test
    void shouldThrowDomainException_whenTheCodeIsExpired() {
        final UserCode userCode = UserCodeBuilderTest.build(UserCodeType.PASSWORD_RESET, TimeUtils.now().minusDays(1));

        final ValidatePasswordResetCodeUseCaseInput input = ValidatePasswordResetCodeUseCaseInput.with(userCode.getCode());

        Mockito.when(this.userCodeRepository.findByCodeAndCodeType(Mockito.any(), Mockito.any())).thenReturn(Optional.of(userCode));

        Mockito.doNothing().when(this.userCodeRepository).deleteById(Mockito.any());

        final DomainException ex = Assertions.assertThrows(DomainException.class, () ->
                this.validatePasswordResetCodeUseCase.execute(input)
        );

        final String expectedErrorMessage = "User code has expired";
        final int expectedStatusCode = 410;

        Assertions.assertInstanceOf(DomainException.class, ex);
        Assertions.assertEquals(expectedErrorMessage, ex.getMessage());
        Assertions.assertEquals(expectedStatusCode, ex.getStatusCode());

        Mockito.verify(this.userCodeRepository, Mockito.times(1)).findByCodeAndCodeType(Mockito.any(), Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).deleteById(Mockito.any());
    }
}
