package com.miguel.jobnest.infrastructure.web.controllers;

import com.miguel.jobnest.infrastructure.web.dtos.res.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> registerUser() {
        return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.from("User registered successfully"));
    }
}
