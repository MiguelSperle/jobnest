package com.miguel.jobnest.application.usecases.user;

import com.miguel.jobnest.application.abstractions.providers.PasswordEncryption;
import com.miguel.jobnest.application.abstractions.repositories.UserCodeRepository;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.wrapper.TransactionExecutor;
import com.miguel.jobnest.application.abstractions.usecases.user.ResetUserPasswordUseCase;
import com.miguel.jobnest.application.usecases.user.inputs.ResetUserPasswordUseCaseInput;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.entities.UserCode;
import com.miguel.jobnest.domain.enums.UserCodeType;
import com.miguel.jobnest.domain.exceptions.DomainException;
import com.miguel.jobnest.domain.exceptions.NotFoundException;
import com.miguel.jobnest.domain.utils.TimeUtils;

public class DefaultResetUserPasswordUseCase implements ResetUserPasswordUseCase {
    private final UserRepository userRepository;
    private final UserCodeRepository userCodeRepository;
    private final PasswordEncryption passwordEncryption;
    private final TransactionExecutor transactionExecutor;

    public DefaultResetUserPasswordUseCase(
            final UserRepository userRepository,
            final UserCodeRepository userCodeRepository,
            final PasswordEncryption passwordEncryption,
            final TransactionExecutor transactionExecutor
    ) {
        this.userRepository = userRepository;
        this.userCodeRepository = userCodeRepository;
        this.passwordEncryption = passwordEncryption;
        this.transactionExecutor = transactionExecutor;
    }

    @Override
    public void execute(final ResetUserPasswordUseCaseInput input) {
        final UserCode userCode = this.getUserCodeByCodeAndCodeType(input.code());

        if (TimeUtils.isExpired(userCode.getExpiresIn(), TimeUtils.now())) {
            this.deleteUserCodeById(userCode.getId());
            throw DomainException.with("User code has expired", 410);
        }

        final User user = this.getUserById(userCode.getUserId());

        final String encodedPassword = this.passwordEncryption.encode(input.password());

        final User updatedUser = user.withPassword(encodedPassword);

        this.transactionExecutor.runTransaction(() -> {
            this.saveUser(updatedUser);
            this.deleteUserCodeById(userCode.getId());
        });
    }

    private UserCode getUserCodeByCodeAndCodeType(final String code) {
        return this.userCodeRepository.findByCodeAndCodeType(code, UserCodeType.PASSWORD_RESET.name())
                .orElseThrow(() -> NotFoundException.with("User code not found"));
    }

    private void deleteUserCodeById(final String id) {
        this.userCodeRepository.deleteById(id);
    }

    private User getUserById(final String id) {
        return this.userRepository.findById(id).orElseThrow(() -> NotFoundException.with("User not found"));
    }

    private void saveUser(final User user) {
        this.userRepository.save(user);
    }
}
