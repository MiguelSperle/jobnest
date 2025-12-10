package com.miguel.jobnest.usecases.user;

import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.services.SecurityService;
import com.miguel.jobnest.application.usecases.user.DefaultUpdateUserUseCase;
import com.miguel.jobnest.application.usecases.user.inputs.UpdateUserUseCaseInput;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.enums.AuthorizationRole;
import com.miguel.jobnest.domain.enums.UserStatus;
import com.miguel.jobnest.domain.exceptions.NotFoundException;
import com.miguel.jobnest.utils.UserBuilderTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

@ExtendWith(MockitoExtension.class)
public class UpdateUserUseCaseTest {
    @InjectMocks
    private DefaultUpdateUserUseCase useCase;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityService securityService;

    @Test
    void shouldUpdateUser() {
        final User user = UserBuilderTest.build(UserStatus.VERIFIED, AuthorizationRole.CANDIDATE);
        final String name = "Alex";
        final String email = "alexhand1898@gmail.com";
        final String description = "This is my description about me";
        final String city = "New York City";
        final String state = "New York";
        final String country = "United States";

        final UpdateUserUseCaseInput input = UpdateUserUseCaseInput.with(
                name,
                email,
                description,
                city,
                state,
                country
        );

        Mockito.when(this.securityService.getPrincipal()).thenReturn(user.getId());
        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(this.userRepository.save(Mockito.any())).thenAnswer(returnsFirstArg());

        this.useCase.execute(input);

        Mockito.verify(this.securityService, Mockito.times(1)).getPrincipal();
        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.userRepository, Mockito.times(1)).save(Mockito.argThat(userSaved ->
                Objects.equals(userSaved.getName(), input.name()) &&
                        Objects.equals(userSaved.getEmail(), input.email()) &&
                        Objects.equals(userSaved.getDescription(), input.description()) &&
                        Objects.equals(userSaved.getCity(), input.city()) &&
                        Objects.equals(userSaved.getState(), input.state()) &&
                        Objects.equals(userSaved.getCountry(), input.country())
        ));
    }

    @Test
    void shouldThrowNotFoundException_whenUserDoesNotExist() {
        final String name = "Alex";
        final String email = "alexhand1898@gmail.com";
        final String description = "This is my description about me";
        final String city = "New York City";
        final String state = "New York";
        final String country = "United States";


        final UpdateUserUseCaseInput input = UpdateUserUseCaseInput.with(
                name,
                email,
                description,
                city,
                state,
                country
        );

        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final NotFoundException ex = Assertions.assertThrows(NotFoundException.class, () ->
                this.useCase.execute(input)
        );

        final String expectedErrorMessage = "User not found";

        Assertions.assertInstanceOf(NotFoundException.class, ex);
        Assertions.assertEquals(expectedErrorMessage, ex.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
    }
}
