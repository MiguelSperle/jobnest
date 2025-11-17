package com.miguel.jobnest.infrastructure.rest.controllers;

import com.miguel.jobnest.application.abstractions.usecases.usercode.SendPasswordResetCodeUseCase;
import com.miguel.jobnest.application.abstractions.usecases.usercode.ResendVerificationCodeUseCase;
import com.miguel.jobnest.infrastructure.rest.dtos.usercode.req.SendPasswordResetCodeRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.usercode.req.ResendVerificationCodeRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.common.res.MessageResponse;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
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
    private final SendPasswordResetCodeUseCase sendPasswordResetCodeUseCase;

    @PostMapping("/verification/resending")
    @RateLimiter(name = "rateLimitConfiguration")
    public ResponseEntity<MessageResponse> resendVerificationCode(@RequestBody @Valid ResendVerificationCodeRequest resendVerificationCodeRequest) {
        this.resendVerificationCodeUseCase.execute(resendVerificationCodeRequest.toInput());

        return ResponseEntity.ok().body(MessageResponse.from("Verification code sent again successfully"));
    }

    @PostMapping("/password-recovery")
    @RateLimiter(name = "rateLimitConfiguration")
    public ResponseEntity<MessageResponse> sendPasswordResetCode(@RequestBody @Valid SendPasswordResetCodeRequest sendPasswordResetCodeRequest) {
        this.sendPasswordResetCodeUseCase.execute(sendPasswordResetCodeRequest.toInput());

        return ResponseEntity.ok().body(MessageResponse.from("Password reset code sent successfully"));
    }
}
