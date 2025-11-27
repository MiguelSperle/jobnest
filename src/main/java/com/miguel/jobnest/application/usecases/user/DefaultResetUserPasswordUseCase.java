package com.miguel.jobnest.application.usecases.user;

import com.miguel.jobnest.application.abstractions.providers.PasswordEncryptionProvider;
import com.miguel.jobnest.application.abstractions.repositories.UserCodeRepository;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.transaction.TransactionExecutor;
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
    private final PasswordEncryptionProvider passwordEncryptionProvider;
    private final TransactionExecutor transactionExecutor;

    public DefaultResetUserPasswordUseCase(
            UserRepository userRepository,
            UserCodeRepository userCodeRepository,
            PasswordEncryptionProvider passwordEncryptionProvider,
            TransactionExecutor transactionExecutor
    ) {
        this.userRepository = userRepository;
        this.userCodeRepository = userCodeRepository;
        this.passwordEncryptionProvider = passwordEncryptionProvider;
        this.transactionExecutor = transactionExecutor;
    }

    @Override
    public void execute(ResetUserPasswordUseCaseInput input) {
        final UserCode userCode = this.getUserCodeByCodeAndCodeType(input.code());

        if (TimeUtils.isExpired(userCode.getExpiresIn(), TimeUtils.now())) {
            this.deleteUserCodeById(userCode.getId());
            throw DomainException.with("User code has expired", 410);
        }

        final User user = this.getUserById(userCode.getUserId());

        final String encodedPassword = this.passwordEncryptionProvider.encode(input.password());

        final User updatedUser = user.withPassword(encodedPassword);

        this.transactionExecutor.runTransaction(() -> {
            this.saveUser(updatedUser);
            this.deleteUserCodeById(userCode.getId());
        });
    }

    private UserCode getUserCodeByCodeAndCodeType(String code) {
        return this.userCodeRepository.findByCodeAndCodeType(code, UserCodeType.PASSWORD_RESET.name())
                .orElseThrow(() -> NotFoundException.with("User code not found"));
    }

    private void deleteUserCodeById(String id) {
        this.userCodeRepository.deleteById(id);
    }

    private User getUserById(String id) {
        return this.userRepository.findById(id).orElseThrow(() -> NotFoundException.with("User not found"));
    }

    private void saveUser(User user) {
        this.userRepository.save(user);
    }
}
