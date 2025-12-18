package com.miguel.jobnest.infrastructure.providers;

import com.miguel.jobnest.application.abstractions.providers.PasswordEncryption;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordEncryptionImpl implements PasswordEncryption {
    private final PasswordEncoder passwordEncoder;

    @Override
    public String encode(String password) {
        return this.passwordEncoder.encode(password);
    }

    @Override
    public boolean matches(String password, String encodedPassword) {
        return this.passwordEncoder.matches(password, encodedPassword);
    }
}
