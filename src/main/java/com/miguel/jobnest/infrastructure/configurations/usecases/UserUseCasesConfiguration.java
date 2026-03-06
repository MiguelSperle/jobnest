package com.miguel.jobnest.infrastructure.configurations.usecases;

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
            final UserRepository userRepository,
            final UserCodeRepository userCodeRepository,
            final PasswordEncryption passwordEncryption,
            final CodeGenerator codeGenerator,
            final TransactionExecutor transactionExecutor
    ) {
        return new DefaultCreateUserUseCase(
                userRepository,
                userCodeRepository,
                passwordEncryption,
                codeGenerator,
                transactionExecutor
        );
    }

    @Bean
    public UpdateUserToVerifiedUseCase updateUserToVerifiedUseCase(
            final UserCodeRepository userCodeRepository,
            final UserRepository userRepository,
            final TransactionExecutor transactionExecutor
    ) {
        return new DefaultUpdateUserToVerifiedUseCase(
                userCodeRepository,
                userRepository,
                transactionExecutor
        );
    }

    @Bean
    public ResetUserPasswordUseCase resetUserPasswordUseCase(
            final UserRepository userRepository,
            final UserCodeRepository userCodeRepository,
            final PasswordEncryption passwordEncryption,
            final TransactionExecutor transactionExecutor
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
            final UserRepository userRepository,
            final PasswordEncryption passwordEncryption,
            final JwtService jwtService
    ) {
        return new DefaultAuthenticateUserUseCase(
                userRepository,
                passwordEncryption,
                jwtService
        );
    }

    @Bean
    public GetAuthenticatedUserUseCase getAuthenticatedUserUseCase(
            final UserRepository userRepository,
            final SecurityService securityService
    ) {
        return new DefaultGetAuthenticatedUserUseCase(
                userRepository,
                securityService
        );
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(
            final UserRepository userRepository,
            final SecurityService securityService
    ) {
        return new DefaultUpdateUserUseCase(
                userRepository,
                securityService
        );
    }

    @Bean
    public UpdateUserPasswordUseCase updateUserPasswordUseCase(
            final UserRepository userRepository,
            final PasswordEncryption passwordEncryption,
            final SecurityService securityService
    ) {
        return new DefaultUpdateUserPasswordUseCase(
                userRepository,
                passwordEncryption,
                securityService
        );
    }

    @Bean
    public SoftDeleteUserUseCase deleteUserUseCase(
            final UserRepository userRepository,
            final SecurityService securityService
    ) {
        return new DefaultSoftDeleteUserUseCase(
                userRepository,
                securityService
        );
    }
}
