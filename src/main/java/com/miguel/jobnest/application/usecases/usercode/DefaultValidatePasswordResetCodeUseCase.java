package com.miguel.jobnest.application.usecases.usercode;

import com.miguel.jobnest.application.abstractions.repositories.UserCodeRepository;
import com.miguel.jobnest.application.abstractions.usecases.usercode.ValidatePasswordResetCodeUseCase;
import com.miguel.jobnest.application.usecases.usercode.inputs.ValidatePasswordResetCodeUseCaseInput;
import com.miguel.jobnest.domain.entities.UserCode;
import com.miguel.jobnest.domain.enums.UserCodeType;
import com.miguel.jobnest.domain.exceptions.DomainException;
import com.miguel.jobnest.domain.exceptions.NotFoundException;
import com.miguel.jobnest.domain.utils.TimeUtils;

public class DefaultValidatePasswordResetCodeUseCase implements ValidatePasswordResetCodeUseCase {
    private final UserCodeRepository userCodeRepository;

    public DefaultValidatePasswordResetCodeUseCase(final UserCodeRepository userCodeRepository) {
        this.userCodeRepository = userCodeRepository;
    }

    @Override
    public void execute(final ValidatePasswordResetCodeUseCaseInput input) {
        final UserCode userCode = this.getUserCodeByCodeAndCodeType(input.code());

        if (TimeUtils.isExpired(userCode.getExpiresIn(), TimeUtils.now())) {
            this.deleteUserCodeById(userCode.getId());
            throw DomainException.with("User code has expired", 410);
        }
    }

    private UserCode getUserCodeByCodeAndCodeType(final String code) {
        return this.userCodeRepository.findByCodeAndCodeType(code, UserCodeType.PASSWORD_RESET.name())
                .orElseThrow(() -> NotFoundException.with("User code not found"));
    }

    private void deleteUserCodeById(final String id) {
        this.userCodeRepository.deleteById(id);
    }
}
