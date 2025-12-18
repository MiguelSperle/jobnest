package com.miguel.jobnest.infrastructure.configurations.usecases;

import com.miguel.jobnest.application.abstractions.producer.MessageProducer;
import com.miguel.jobnest.application.abstractions.providers.CodeGenerator;
import com.miguel.jobnest.application.abstractions.repositories.UserCodeRepository;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.usecases.usercode.ResendVerificationCodeUseCase;
import com.miguel.jobnest.application.abstractions.usecases.usercode.SendPasswordResetCodeUseCase;
import com.miguel.jobnest.application.abstractions.usecases.usercode.ValidatePasswordResetCodeUseCase;
import com.miguel.jobnest.application.usecases.usercode.DefaultSendPasswordResetCodeUseCase;
import com.miguel.jobnest.application.usecases.usercode.DefaultValidatePasswordResetCodeUseCase;
import com.miguel.jobnest.application.usecases.usercode.DefaultResendVerificationCodeUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserCodeUseCasesConfiguration {
    @Bean
    public ResendVerificationCodeUseCase resendVerificationCodeUseCase(
            UserCodeRepository userCodeRepository,
            UserRepository userRepository,
            MessageProducer messageProducer,
            CodeGenerator codeGenerator
    ) {
        return new DefaultResendVerificationCodeUseCase(
                userCodeRepository,
                userRepository,
                messageProducer,
                codeGenerator
        );
    }

    @Bean
    public SendPasswordResetCodeUseCase sendPasswordResetCodeUseCase(
            UserRepository userRepository,
            UserCodeRepository userCodeRepository,
            CodeGenerator codeGenerator,
            MessageProducer messageProducer
    ) {
        return new DefaultSendPasswordResetCodeUseCase(
                userRepository,
                userCodeRepository,
                codeGenerator,
                messageProducer
        );
    }

    @Bean
    public ValidatePasswordResetCodeUseCase validatePasswordResetCodeUseCase(UserCodeRepository userCodeRepository) {
        return new DefaultValidatePasswordResetCodeUseCase(userCodeRepository);
    }
}
