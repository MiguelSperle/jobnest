package com.miguel.jobnest.infrastructure.configuration.usecases;

import com.miguel.jobnest.application.abstractions.producer.MessageProducer;
import com.miguel.jobnest.application.abstractions.providers.PasswordEncryptionProvider;
import com.miguel.jobnest.application.abstractions.repositories.UserCodeRepository;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.services.CacheService;
import com.miguel.jobnest.application.abstractions.services.JwtService;
import com.miguel.jobnest.application.abstractions.services.SecurityContextService;
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

    @Bean
    public AuthenticateUserUseCase authenticateUserUseCase(
            UserRepository userRepository,
            PasswordEncryptionProvider passwordEncryptionProvider,
            JwtService jwtService
    ) {
        return new AuthenticateUserUseCaseImpl(
                userRepository,
                passwordEncryptionProvider,
                jwtService
        );
    }

    @Bean
    public GetAuthenticatedUserUseCase getAuthenticatedUserUseCase(
            UserRepository userRepository,
            CacheService cacheService,
            SecurityContextService securityContextService
    ) {
        return new GetAuthenticatedUserUseCaseImpl(
                userRepository,
                cacheService,
                securityContextService
        );
    }

    @Bean
    public UpdateUserInformationUseCase updateUserInformationUseCase(
            UserRepository userRepository,
            CacheService cacheService
    ) {
        return new UpdateUserInformationUseCaseImpl(
                userRepository,
                cacheService
        );
    }

    @Bean
    public UpdateUserPasswordUseCase updateUserPasswordUseCase(
            UserRepository userRepository,
            PasswordEncryptionProvider passwordEncryptionProvider
    ) {
        return new UpdateUserPasswordUseCaseImpl(
                userRepository,
                passwordEncryptionProvider
        );
    }

    @Bean
    public DeleteUserUseCase deleteUserUseCase(UserRepository userRepository) {
        return new DeleteUserUseCaseImpl(userRepository);
    }
}
