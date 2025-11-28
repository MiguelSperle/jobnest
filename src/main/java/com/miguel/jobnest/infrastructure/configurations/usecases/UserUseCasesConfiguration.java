package com.miguel.jobnest.infrastructure.configurations.usecases;

import com.miguel.jobnest.application.abstractions.producer.MessageProducer;
import com.miguel.jobnest.application.abstractions.providers.PasswordEncryptionProvider;
import com.miguel.jobnest.application.abstractions.repositories.UserCodeRepository;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.services.JwtService;
import com.miguel.jobnest.application.abstractions.services.SecurityService;
import com.miguel.jobnest.application.abstractions.transaction.TransactionExecutor;
import com.miguel.jobnest.application.abstractions.usecases.user.*;
import com.miguel.jobnest.application.usecases.user.*;
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
        return new DefaultRegisterUserUseCase(
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
        return new DefaultUpdateUserToVerifiedUseCase(
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
        return new DefaultResetUserPasswordUseCase(
                userRepository,
                userCodeRepository,
                passwordEncryptionProvider,
                transactionExecutor
        );
    }

    @Bean
    public AuthenticateUserUseCase authenticateUserUseCase(
            UserRepository userRepository,
            PasswordEncryptionProvider passwordEncryptionProvider,
            JwtService jwtService
    ) {
        return new DefaultAuthenticateUserUseCase(
                userRepository,
                passwordEncryptionProvider,
                jwtService
        );
    }

    @Bean
    public GetAuthenticatedUserUseCase getAuthenticatedUserUseCase(
            UserRepository userRepository,
            SecurityService securityService
    ) {
        return new DefaultGetAuthenticatedUserUseCase(
                userRepository,
                securityService
        );
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(UserRepository userRepository) {
        return new DefaultUpdateUserUseCase(userRepository);
    }

    @Bean
    public UpdateUserPasswordUseCase updateUserPasswordUseCase(
            UserRepository userRepository,
            PasswordEncryptionProvider passwordEncryptionProvider
    ) {
        return new DefaultUpdateUserPasswordUseCase(
                userRepository,
                passwordEncryptionProvider
        );
    }

    @Bean
    public SoftDeleteUserUseCase deleteUserUseCase(UserRepository userRepository) {
        return new DefaultSoftDeleteUserUseCase(userRepository);
    }
}
