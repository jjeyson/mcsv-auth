package org.acme.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PasswordUtilsTest {

    @Test
    void hashesAndVerifiesPassword() {
        String rawPassword = "correct-horse-battery-staple";
        String hash = PasswordUtils.hashPassword(rawPassword);

        assertNotEquals(rawPassword, hash);
        assertTrue(PasswordUtils.verifyPassword(rawPassword, hash));
        assertFalse(PasswordUtils.verifyPassword("wrong-password", hash));
    }
}
