package com.miguel.jobnest.infrastructure.configuration.usecases;

import com.miguel.jobnest.application.abstractions.producer.MessageProducer;
import com.miguel.jobnest.application.abstractions.providers.CodeProvider;
import com.miguel.jobnest.application.abstractions.repositories.UserCodeRepository;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.usecases.usercode.ResendVerificationCodeUseCase;
import com.miguel.jobnest.application.abstractions.usecases.usercode.SendPasswordResetCodeUseCase;
import com.miguel.jobnest.application.usecases.usercode.password.send.SendPasswordResetCodeUseCaseImpl;
import com.miguel.jobnest.application.usecases.usercode.verification.resend.ResendVerificationCodeUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserCodeUseCasesConfiguration {
    @Bean
    public ResendVerificationCodeUseCase resendVerificationCodeUseCase(
            UserCodeRepository userCodeRepository,
            UserRepository userRepository,
            MessageProducer messageProducer,
            CodeProvider codeProvider
    ) {
        return new ResendVerificationCodeUseCaseImpl(
                userCodeRepository,
                userRepository,
                messageProducer,
                codeProvider
        );
    }

    @Bean
    public SendPasswordResetCodeUseCase sendPasswordResetCodeUseCase(
            UserRepository userRepository,
            UserCodeRepository userCodeRepository,
            CodeProvider codeProvider,
            MessageProducer messageProducer
    ) {
        return new SendPasswordResetCodeUseCaseImpl(
                userRepository,
                userCodeRepository,
                codeProvider,
                messageProducer
        );
    }
}
