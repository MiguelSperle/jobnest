package com.miguel.jobnest.infrastructure.configuration.usecases.user;

import com.miguel.jobnest.application.abstractions.producer.MessageProducer;
import com.miguel.jobnest.application.abstractions.providers.PasswordEncryptorProvider;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.usecases.user.RegisterUserUseCase;
import com.miguel.jobnest.application.usecases.user.RegisterUserUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserUseCasesConfiguration {
    @Bean
    public RegisterUserUseCase createUserUseCase(
            UserRepository userRepository,
            PasswordEncryptorProvider passwordEncryptorProvider,
            MessageProducer messageProducer
    ) {
        return new RegisterUserUseCaseImpl(
                userRepository,
                passwordEncryptorProvider,
                messageProducer
        );
    }
}
