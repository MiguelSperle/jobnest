package com.miguel.jobnest.infrastructure.abstractions.rest.controllers;

import com.miguel.jobnest.infrastructure.rest.dtos.MessageResponse;
import com.miguel.jobnest.infrastructure.rest.dtos.usercode.req.ResendVerificationCodeRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.usercode.req.SendPasswordResetCodeRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/user-codes")
public interface UserCodeControllerAPI {
    @PostMapping("/verification/resending")
    ResponseEntity<MessageResponse> resendVerificationCode(@RequestBody @Valid ResendVerificationCodeRequest request);

    @PostMapping("/password-recovery")
    ResponseEntity<MessageResponse> sendPasswordResetCode(@RequestBody @Valid SendPasswordResetCodeRequest request);

    @GetMapping("/password-recovery/{code}/validation")
    ResponseEntity<Void> validatePasswordResetCode(@PathVariable String code);
}
