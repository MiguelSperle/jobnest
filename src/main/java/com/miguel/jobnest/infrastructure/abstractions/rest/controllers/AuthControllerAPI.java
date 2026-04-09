package com.miguel.jobnest.infrastructure.abstractions.rest.controllers;

import com.miguel.jobnest.infrastructure.rest.dtos.MessageResponse;
import com.miguel.jobnest.infrastructure.rest.dtos.user.req.AuthenticateUserRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.user.req.CreateUserRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.user.res.AuthenticateUserResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/auth")
public interface AuthControllerAPI {
    @PostMapping("/create")
    ResponseEntity<MessageResponse> createUser(@RequestBody @Valid CreateUserRequest request);

    @PostMapping("/login")
    ResponseEntity<AuthenticateUserResponse> authenticateUser(@RequestBody @Valid AuthenticateUserRequest request);
}
