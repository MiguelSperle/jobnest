package com.miguel.jobnest.usecases.user;

import com.miguel.jobnest.application.abstractions.repositories.UserRepository;
import com.miguel.jobnest.application.abstractions.services.SecurityService;
import com.miguel.jobnest.application.usecases.user.DefaultSoftDeleteUserUseCase;
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
public class SoftDeleteUserUseCaseTest {
    @InjectMocks
    private DefaultSoftDeleteUserUseCase useCase;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityService securityService;

    @Test
    void shouldDeleteUser() {
        final User user = UserBuilderTest.build(UserStatus.VERIFIED, AuthorizationRole.CANDIDATE);

        Mockito.when(this.securityService.getPrincipal()).thenReturn(user.getId());
        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(this.userRepository.save(Mockito.any())).thenAnswer(returnsFirstArg());

        this.useCase.execute();

        Mockito.verify(this.securityService, Mockito.times(1)).getPrincipal();
        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(this.userRepository, Mockito.times(1)).save(Mockito.argThat(userSaved ->
                Objects.equals(userSaved.getUserStatus(), UserStatus.DELETED)
        ));
    }

    @Test
    void shouldThrowNotFoundException_whenUserDoesNotExist() {
        Mockito.when(this.userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        final NotFoundException ex = Assertions.assertThrows(NotFoundException.class, () ->
                this.useCase.execute()
        );

        final String expectedErrorMessage = "User not found";

        Assertions.assertInstanceOf(NotFoundException.class, ex);
        Assertions.assertEquals(expectedErrorMessage, ex.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findById(Mockito.any());
    }
}
