package com.miguel.jobnest.infrastructure.configuration.usecases;

import com.miguel.jobnest.application.abstractions.producer.MessageProducer;
import com.miguel.jobnest.application.abstractions.providers.PasswordEncryptionProvider;
import com.miguel.jobnest.application.abstractions.repositories.UserCodeRepository;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.transaction.TransactionExecutor;
import com.miguel.jobnest.application.abstractions.usecases.user.register.RegisterUserUseCase;
import com.miguel.jobnest.application.abstractions.usecases.user.reset.password.ResetUserPasswordUseCase;
import com.miguel.jobnest.application.abstractions.usecases.user.update.status.UpdateUserToVerifiedUseCase;
import com.miguel.jobnest.application.usecases.user.register.RegisterUserUseCaseImpl;
import com.miguel.jobnest.application.usecases.user.reset.password.ResetUserPasswordUseCaseImpl;
import com.miguel.jobnest.application.usecases.user.update.status.UpdateUserToVerifiedUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserUseCasesConfiguration {
    @Bean
    public RegisterUserUseCase registerUserUseCase(
            UserRepository userRepository,
            PasswordEncryptionProvider passwordEncryptionProvider,
            MessageProducer messageProducer
    ) {
        return new RegisterUserUseCaseImpl(
                userRepository,
                passwordEncryptionProvider,
                messageProducer
        );
    }

    @Bean
    public UpdateUserToVerifiedUseCase updateUserToVerifiedUseCase(
            UserCodeRepository userCodeRepository,
            UserRepository userRepository,
            TransactionExecutor transactionExecutor
    ) {
        return new UpdateUserToVerifiedUseCaseImpl(
                userCodeRepository,
                userRepository,
                transactionExecutor
        );
    }

    @Bean
    public ResetUserPasswordUseCase resetUserPasswordUseCase(
            UserRepository userRepository,
            UserCodeRepository userCodeRepository,
            PasswordEncryptionProvider passwordEncryptionProvider,
            TransactionExecutor transactionExecutor
    ) {
        return new ResetUserPasswordUseCaseImpl(
                userRepository,
                userCodeRepository,
                passwordEncryptionProvider,
                transactionExecutor
        );
    }
}
