package com.miguel.jobnest.infrastructure.rest.controllers;

import com.miguel.jobnest.application.abstractions.usecases.user.UpdateUserToVerifiedUseCase;
import com.miguel.jobnest.application.usecases.user.inputs.UpdateUserToVerifiedUseCaseInput;
import com.miguel.jobnest.infrastructure.rest.dtos.res.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UpdateUserToVerifiedUseCase updateUserToVerifiedUseCase;

    @PatchMapping("/verification/{code}")
    public ResponseEntity<MessageResponse> updateUserToVerified(@PathVariable String code) {
        this.updateUserToVerifiedUseCase.execute(UpdateUserToVerifiedUseCaseInput.with(code));

        return ResponseEntity.ok().body(MessageResponse.from("User verified successfully"));
    }
}
