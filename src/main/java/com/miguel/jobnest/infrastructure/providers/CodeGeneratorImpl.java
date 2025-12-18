package com.miguel.jobnest.infrastructure.providers;

import com.miguel.jobnest.application.abstractions.providers.CodeGenerator;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class CodeGeneratorImpl implements CodeGenerator {
    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public String generateCode(int codeLength, String characters) {
        final StringBuilder code = new StringBuilder(codeLength);
        final int charSetLength = characters.length();

        for (int i = 0; i < codeLength; i++) {
            final int randomIndex = this.secureRandom.nextInt(charSetLength);
            code.append(characters.charAt(randomIndex));
        }

        return code.toString();
    }
}
