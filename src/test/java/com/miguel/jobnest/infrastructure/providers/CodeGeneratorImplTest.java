package com.miguel.jobnest.infrastructure.providers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CodeGeneratorImplTest {
    private final CodeGeneratorImpl codeGenerator = new CodeGeneratorImpl();

    @Test
    void shouldReturnCode_whenCallGenerateCode() {
        final int codeLength = 8;
        final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        final String codeGenerated = this.codeGenerator.generateCode(codeLength, characters);

        Assertions.assertNotNull(codeGenerated);
        Assertions.assertEquals(codeLength, codeGenerated.length());
    }
}
