package com.miguel.jobnest.infrastructure.abstractions.rest.controllers;

import com.miguel.jobnest.infrastructure.rest.dtos.MessageResponse;
import com.miguel.jobnest.infrastructure.rest.dtos.user.req.ResetUserPasswordRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.user.req.UpdateUserPasswordRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.user.req.UpdateUserRequest;
import com.miguel.jobnest.infrastructure.rest.dtos.user.res.GetAuthenticatedUserResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/users")
public interface UserControllerAPI {
    @PatchMapping("/verification/{code}")
    ResponseEntity<MessageResponse> updateUserToVerified(@PathVariable String code);

    @PatchMapping("/password-reset/{code}")
    ResponseEntity<MessageResponse> resetUserPassword(@PathVariable String code, @RequestBody @Valid ResetUserPasswordRequest request);

    @GetMapping("/me")
    ResponseEntity<GetAuthenticatedUserResponse> getAuthenticatedUser(@AuthenticationPrincipal String userId);

    @PatchMapping("/update/information")
    ResponseEntity<MessageResponse> updateUser(@RequestBody @Valid UpdateUserRequest request, @AuthenticationPrincipal String userId);

    @PatchMapping("/update/password")
    ResponseEntity<MessageResponse> updateUserPassword(@RequestBody @Valid UpdateUserPasswordRequest request, @AuthenticationPrincipal String userId);

    @DeleteMapping("/delete")
    ResponseEntity<MessageResponse> deleteUser(@AuthenticationPrincipal String userId);
}
