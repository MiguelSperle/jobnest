package com.miguel.jobnest.application.usecases.user;

import com.miguel.jobnest.application.abstractions.repositories.UserCodeRepository;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.wrapper.TransactionExecutor;
import com.miguel.jobnest.application.abstractions.usecases.user.UpdateUserToVerifiedUseCase;
import com.miguel.jobnest.application.usecases.user.inputs.UpdateUserToVerifiedUseCaseInput;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.entities.UserCode;
import com.miguel.jobnest.domain.enums.UserCodeType;
import com.miguel.jobnest.domain.enums.UserStatus;
import com.miguel.jobnest.domain.exceptions.DomainException;
import com.miguel.jobnest.domain.exceptions.NotFoundException;
import com.miguel.jobnest.domain.utils.TimeUtils;

public class DefaultUpdateUserToVerifiedUseCase implements UpdateUserToVerifiedUseCase {
    private final UserCodeRepository userCodeRepository;
    private final UserRepository userRepository;
    private final TransactionExecutor transactionExecutor;

    public DefaultUpdateUserToVerifiedUseCase(
            final UserCodeRepository userCodeRepository,
            final UserRepository userRepository,
            final TransactionExecutor transactionExecutor
    ) {
        this.userCodeRepository = userCodeRepository;
        this.userRepository = userRepository;
        this.transactionExecutor = transactionExecutor;
    }

    @Override
    public void execute(final UpdateUserToVerifiedUseCaseInput input) {
        final UserCode userCode = this.getUserCodeByCodeAndCodeType(input.code());

        if (TimeUtils.isExpired(userCode.getExpiresIn(), TimeUtils.now())) {
            this.deleteUserCodeById(userCode.getId());
            throw DomainException.with("User code has expired", 410);
        }

        final User user = this.getUserById(userCode.getUserId());

        final User updatedUser = user.withUserStatus(UserStatus.VERIFIED);

        this.transactionExecutor.runTransaction(() -> {
            this.saveUser(updatedUser);
            this.deleteUserCodeById(userCode.getId());
        });
    }

    private UserCode getUserCodeByCodeAndCodeType(final String code) {
        return this.userCodeRepository.findByCodeAndCodeType(code, UserCodeType.USER_VERIFICATION.name())
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
