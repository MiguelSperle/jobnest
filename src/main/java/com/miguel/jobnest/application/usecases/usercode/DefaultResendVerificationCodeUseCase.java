package com.miguel.jobnest.application.usecases.usercode;

import com.miguel.jobnest.application.abstractions.providers.CodeGenerator;
import com.miguel.jobnest.application.abstractions.repositories.UserCodeRepository;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.usecases.usercode.ResendVerificationCodeUseCase;
import com.miguel.jobnest.application.usecases.usercode.inputs.ResendVerificationCodeUseCaseInput;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.entities.UserCode;
import com.miguel.jobnest.domain.enums.UserCodeType;
import com.miguel.jobnest.domain.enums.UserStatus;
import com.miguel.jobnest.domain.events.UserCodeCreatedEvent;
import com.miguel.jobnest.domain.exceptions.DomainException;
import com.miguel.jobnest.domain.exceptions.NotFoundException;

import java.util.Optional;

public class DefaultResendVerificationCodeUseCase implements ResendVerificationCodeUseCase {
    private final UserCodeRepository userCodeRepository;
    private final UserRepository userRepository;
    private final CodeGenerator codeGenerator;

    private final static int CODE_LENGTH = 8;
    private final static String ALPHANUMERIC_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public DefaultResendVerificationCodeUseCase(
            final UserCodeRepository userCodeRepository,
            final UserRepository userRepository,
            final CodeGenerator codeGenerator
    ) {
        this.userCodeRepository = userCodeRepository;
        this.userRepository = userRepository;
        this.codeGenerator = codeGenerator;
    }

    @Override
    public void execute(final ResendVerificationCodeUseCaseInput input) {
        final User user = this.getUserByEmail(input.email());

        if (user.getUserStatus() == UserStatus.VERIFIED) {
            throw DomainException.with("User has already been verified", 409);
        }

        if (user.getUserStatus() == UserStatus.DELETED) {
            throw DomainException.with("User has already been deleted", 409);
        }

        this.getPreviousUserCodeByUserIdAndCodeType(user.getId()).ifPresent(userCode ->
                this.deleteUserCodeById(userCode.getId())
        );

        final String codeGenerated = this.codeGenerator.generateCode(CODE_LENGTH, ALPHANUMERIC_CHARACTERS);

        final UserCode newUserCode = UserCode.newUserCode(user.getId(), codeGenerated, UserCodeType.USER_VERIFICATION);

        newUserCode.registerEvent(new UserCodeCreatedEvent(
                newUserCode.getId(),
                newUserCode.getCode(),
                newUserCode.getUserCodeType(),
                newUserCode.getUserId()
        ));

        this.saveUserCode(newUserCode);
    }

    private User getUserByEmail(final String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> NotFoundException.with("User not found"));
    }

    private Optional<UserCode> getPreviousUserCodeByUserIdAndCodeType(final String userId) {
        return this.userCodeRepository.findByUserIdAndCodeType(userId, UserCodeType.USER_VERIFICATION.name());
    }

    private void deleteUserCodeById(final String id) {
        this.userCodeRepository.deleteById(id);
    }

    private void saveUserCode(final UserCode userCode) {
         this.userCodeRepository.save(userCode);
    }
}
