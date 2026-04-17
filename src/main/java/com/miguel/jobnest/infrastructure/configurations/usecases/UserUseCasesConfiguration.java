package com.miguel.jobnest.infrastructure.configurations.usecases;

import com.miguel.jobnest.application.abstractions.providers.CodeGenerator;
import com.miguel.jobnest.application.abstractions.providers.PasswordEncryption;
import com.miguel.jobnest.application.abstractions.repositories.UserCodeRepository;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.services.JwtGeneratorService;
import com.miguel.jobnest.application.abstractions.wrapper.TransactionManager;
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
            final TransactionManager transactionManager
    ) {
        return new DefaultCreateUserUseCase(
                userRepository,
                userCodeRepository,
                passwordEncryption,
                codeGenerator,
                transactionManager
        );
    }

    @Bean
    public UpdateUserToVerifiedUseCase updateUserToVerifiedUseCase(
            final UserCodeRepository userCodeRepository,
            final UserRepository userRepository,
            final TransactionManager transactionManager
    ) {
        return new DefaultUpdateUserToVerifiedUseCase(
                userCodeRepository,
                userRepository,
                transactionManager
        );
    }

    @Bean
    public ResetUserPasswordUseCase resetUserPasswordUseCase(
            final UserRepository userRepository,
            final UserCodeRepository userCodeRepository,
            final PasswordEncryption passwordEncryption,
            final TransactionManager transactionManager
    ) {
        return new DefaultResetUserPasswordUseCase(
                userRepository,
                userCodeRepository,
                passwordEncryption,
                transactionManager
        );
    }

    @Bean
    public AuthenticateUserUseCase authenticateUserUseCase(
            final UserRepository userRepository,
            final PasswordEncryption passwordEncryption,
            final JwtGeneratorService jwtGeneratorService
    ) {
        return new DefaultAuthenticateUserUseCase(
                userRepository,
                passwordEncryption,
                jwtGeneratorService
        );
    }

    @Bean
    public GetAuthenticatedUserUseCase getAuthenticatedUserUseCase(final UserRepository userRepository) {
        return new DefaultGetAuthenticatedUserUseCase(userRepository);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(final UserRepository userRepository) {
        return new DefaultUpdateUserUseCase(userRepository);
    }

    @Bean
    public UpdateUserPasswordUseCase updateUserPasswordUseCase(
            final UserRepository userRepository,
            final PasswordEncryption passwordEncryption
    ) {
        return new DefaultUpdateUserPasswordUseCase(
                userRepository,
                passwordEncryption
        );
    }

    @Bean
    public SoftDeleteUserUseCase deleteUserUseCase(
            final UserRepository userRepository
    ) {
        return new DefaultSoftDeleteUserUseCase(
                userRepository
        );
    }
}
