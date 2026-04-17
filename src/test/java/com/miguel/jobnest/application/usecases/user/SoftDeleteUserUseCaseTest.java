package com.miguel.jobnest.application.usecases.user;

import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.usecases.user.inputs.SoftDeleteUserUseCaseInput;
import com.miguel.jobnest.domain.builders.UserBuilder;
import com.miguel.jobnest.domain.entities.User;
import com.miguel.jobnest.domain.enums.UserStatus;
import com.miguel.jobnest.domain.exceptions.NotFoundException;
import com.miguel.jobnest.domain.utils.IdentifierUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

@ExtendWith(MockitoExtension.class)
public class SoftDeleteUserUseCaseTest {
    @InjectMocks
    private DefaultSoftDeleteUserUseCase useCase;

    @Mock
    private UserRepository userRepository;

    @Test
    void shouldDeleteUser_whenCallExecute() {
        final User user = UserBuilder.user().id(IdentifierUtils.generateNewId()).userStatus(UserStatus.VERIFIED).build();

        final SoftDeleteUserUseCaseInput input = SoftDeleteUserUseCaseInput.with(user.getId());

        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(this.userRepository.save(Mockito.any())).thenAnswer(returnsFirstArg());

        this.useCase.execute(input);

        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.userRepository, Mockito.times(1)).save(Mockito.argThat(userSaved ->
                userSaved.getUserStatus() == UserStatus.DELETED
        ));
    }

    @Test
    void shouldThrowNotFoundException_whenCallExecute_becauseUserDoesNotExist() {
        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final SoftDeleteUserUseCaseInput input = SoftDeleteUserUseCaseInput.with(IdentifierUtils.generateNewId());

        final var ex = Assertions.assertThrows(NotFoundException.class, () ->
                this.useCase.execute(input)
        );

        final String expectedErrorMessage = "User not found";

        Assertions.assertEquals(expectedErrorMessage, ex.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
    }
}
