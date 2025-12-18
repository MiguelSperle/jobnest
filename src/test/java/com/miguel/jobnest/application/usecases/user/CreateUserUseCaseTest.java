package com.miguel.jobnest.application.usecases.user;

import com.miguel.jobnest.application.abstractions.producer.MessageProducer;
import com.miguel.jobnest.application.abstractions.providers.CodeGenerator;
import com.miguel.jobnest.application.abstractions.providers.PasswordEncryption;
import com.miguel.jobnest.application.abstractions.repositories.UserCodeRepository;
import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.wrapper.TransactionExecutor;
import com.miguel.jobnest.application.usecases.user.inputs.CreateUserUseCaseInput;
import com.miguel.jobnest.domain.enums.AuthorizationRole;
import com.miguel.jobnest.domain.enums.UserCodeType;
import com.miguel.jobnest.domain.enums.UserStatus;
import com.miguel.jobnest.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

@ExtendWith(MockitoExtension.class)
public class CreateUserUseCaseTest {
    @InjectMocks
    private DefaultCreateUserUseCase useCase;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserCodeRepository userCodeRepository;

    @Mock
    private PasswordEncryption passwordEncryption;

    @Mock
    private CodeGenerator codeGenerator;

    @Mock
    private TransactionExecutor transactionExecutor;

    @Mock
    private MessageProducer messageProducer;

    @Test
    void shouldCreateUser() {
        final String code = "1BC34TD1";

        final String name = "Robert";
        final String email = "robertrenan1947@gmail.com";
        final String password = "12345";
        final String authorizationRole = "CANDIDATE";
        final String city = "Rio de Janeiro";
        final String state = "Rio de Janeiro";
        final String country = "Brazil";

        final CreateUserUseCaseInput input = CreateUserUseCaseInput.with(
                name,
                email,
                password,
                authorizationRole,
                city,
                state,
                country
        );

        Mockito.when(this.userRepository.existsByEmail(Mockito.any())).thenReturn(false);
        Mockito.when(this.passwordEncryption.encode(Mockito.any())).thenReturn(input.password());
        Mockito.doAnswer(invocationOnMock -> {
            Runnable runnable = invocationOnMock.getArgument(0);
            runnable.run();
            return runnable;
        }).when(this.transactionExecutor).runTransaction(Mockito.any());
        Mockito.when(this.userRepository.save(Mockito.any())).thenAnswer(returnsFirstArg());
        Mockito.when(this.codeGenerator.generateCode(Mockito.anyInt(), Mockito.any())).thenReturn(code);
        Mockito.when(this.userCodeRepository.save(Mockito.any())).thenAnswer(returnsFirstArg());
        Mockito.doAnswer(invocationOnMock -> {
            final Runnable runnable = invocationOnMock.getArgument(0);
            runnable.run();
            return runnable;
        }).when(this.transactionExecutor).makeAfterCommit(Mockito.any());
        Mockito.doNothing().when(this.messageProducer).publish(Mockito.any(), Mockito.any(), Mockito.any());

        this.useCase.execute(input);

        Mockito.verify(this.userRepository, Mockito.times(1)).existsByEmail(Mockito.any());
        Mockito.verify(this.passwordEncryption, Mockito.times(1)).encode(Mockito.any());
        Mockito.verify(this.transactionExecutor, Mockito.times(1)).runTransaction(Mockito.any());
        Mockito.verify(this.userRepository, Mockito.times(1)).save(Mockito.argThat(userSaved ->
                Objects.nonNull(userSaved.getId()) &&
                        Objects.equals(userSaved.getName(), input.name()) &&
                        Objects.equals(userSaved.getEmail(), input.email()) &&
                        Objects.isNull(userSaved.getDescription()) &&
                        Objects.equals(userSaved.getPassword(), input.password()) &&
                        Objects.equals(userSaved.getUserStatus(), UserStatus.UNVERIFIED) &&
                        Objects.equals(userSaved.getAuthorizationRole(), AuthorizationRole.CANDIDATE) &&
                        Objects.equals(userSaved.getCity(), input.city()) &&
                        Objects.equals(userSaved.getState(), input.state()) &&
                        Objects.equals(userSaved.getCountry(), input.country()) &&
                        Objects.nonNull(userSaved.getCreatedAt())
        ));
        Mockito.verify(this.codeGenerator, Mockito.times(1)).generateCode(Mockito.anyInt(), Mockito.any());
        Mockito.verify(this.userCodeRepository, Mockito.times(1)).save(Mockito.argThat(userCodeSaved ->
                Objects.nonNull(userCodeSaved.getId()) &&
                        Objects.nonNull(userCodeSaved.getUserId()) &&
                        Objects.equals(userCodeSaved.getCode(), code) &&
                        Objects.equals(userCodeSaved.getUserCodeType(), UserCodeType.USER_VERIFICATION) &&
                        Objects.nonNull(userCodeSaved.getExpiresIn()) &&
                        Objects.nonNull(userCodeSaved.getCreatedAt())
        ));
        Mockito.verify(this.transactionExecutor, Mockito.times(1)).makeAfterCommit(Mockito.any());
        Mockito.verify(this.messageProducer, Mockito.times(1)).publish(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    void shouldThrowDomainException_whenEmailIsAlreadyBeingUsed() {
        final String name = "Robert";
        final String email = "robertrenan1947@gmail.com";
        final String password = "12345";
        final String authorizationRole = "CANDIDATE";
        final String city = "Rio de Janeiro";
        final String state = "Rio de Janeiro";
        final String country = "Brazil";

        final CreateUserUseCaseInput input = CreateUserUseCaseInput.with(
                name,
                email,
                password,
                authorizationRole,
                city,
                state,
                country
        );

        Mockito.when(this.userRepository.existsByEmail(Mockito.any())).thenReturn(true);

        final var ex = Assertions.assertThrows(DomainException.class, () ->
                this.useCase.execute(input)
        );

        final String expectedErrorMessage = "This email is already being used";
        final int expectedStatusCode = 409;

        Assertions.assertEquals(expectedErrorMessage, ex.getMessage());
        Assertions.assertEquals(expectedStatusCode, ex.getStatusCode());

        Mockito.verify(this.userRepository, Mockito.times(1)).existsByEmail(Mockito.any());
    }
}
