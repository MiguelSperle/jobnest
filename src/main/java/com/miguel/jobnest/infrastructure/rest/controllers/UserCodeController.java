package com.miguel.jobnest.infrastructure.rest.controllers;

import com.miguel.jobnest.application.abstractions.usecases.usercode.ResendVerificationCodeUseCase;
import com.miguel.jobnest.infrastructure.rest.dtos.usercode.req.ResendVerificationCodeRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user-codes")
@RequiredArgsConstructor
public class UserCodeController {
    private final ResendVerificationCodeUseCase resendVerificationCodeUseCase;

    @PostMapping("/verification/resending")
    public ResponseEntity<MessageResponse> resendVerificationCode(@RequestBody @Valid ResendVerificationCodeRequest resendVerificationCodeRequest) {
        this.resendVerificationCodeUseCase.execute(resendVerificationCodeRequest.toInput());

        return ResponseEntity.ok().body(MessageResponse.from("Verification code sent again successfully"));
    }
}
