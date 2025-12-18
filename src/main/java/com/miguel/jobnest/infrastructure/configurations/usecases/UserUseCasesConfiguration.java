package com.miguel.jobnest.infrastructure.configurations.usecases;

import com.miguel.jobnest.application.abstractions.producer.MessageProducer;
import com.miguel.jobnest.application.abstractions.providers.CodeGenerator;
import com.miguel.jobnest.application.abstractions.providers.PasswordEncryption;
import com.miguel.jobnest.application.abstractions.repositories.UserCodeRepository;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.services.JwtService;
import com.miguel.jobnest.application.abstractions.services.SecurityService;
import com.miguel.jobnest.application.abstractions.wrapper.TransactionExecutor;
import com.miguel.jobnest.application.abstractions.usecases.user.*;
import com.miguel.jobnest.application.usecases.user.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserUseCasesConfiguration {
    @Bean
    public CreateUserUseCase registerUserUseCase(
            UserRepository userRepository,
            UserCodeRepository userCodeRepository,
            PasswordEncryption passwordEncryption,
            CodeGenerator codeGenerator,
            TransactionExecutor transactionExecutor,
            MessageProducer messageProducer
    ) {
        return new DefaultCreateUserUseCase(
                userRepository,
                userCodeRepository,
                passwordEncryption,
                codeGenerator,
                transactionExecutor,
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
            PasswordEncryption passwordEncryption,
            TransactionExecutor transactionExecutor
    ) {
        return new DefaultResetUserPasswordUseCase(
                userRepository,
                userCodeRepository,
                passwordEncryption,
                transactionExecutor
        );
    }

    @Bean
    public AuthenticateUserUseCase authenticateUserUseCase(
            UserRepository userRepository,
            PasswordEncryption passwordEncryption,
            JwtService jwtService
    ) {
        return new DefaultAuthenticateUserUseCase(
                userRepository,
                passwordEncryption,
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
    public UpdateUserUseCase updateUserUseCase(
            UserRepository userRepository,
            SecurityService securityService
    ) {
        return new DefaultUpdateUserUseCase(
                userRepository,
                securityService
        );
    }

    @Bean
    public UpdateUserPasswordUseCase updateUserPasswordUseCase(
            UserRepository userRepository,
            PasswordEncryption passwordEncryption,
            SecurityService securityService
    ) {
        return new DefaultUpdateUserPasswordUseCase(
                userRepository,
                passwordEncryption,
                securityService
        );
    }

    @Bean
    public SoftDeleteUserUseCase deleteUserUseCase(
            UserRepository userRepository,
            SecurityService securityService
    ) {
        return new DefaultSoftDeleteUserUseCase(
                userRepository,
                securityService
        );
    }
}
