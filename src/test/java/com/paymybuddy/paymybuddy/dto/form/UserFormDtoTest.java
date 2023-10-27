package com.paymybuddy.paymybuddy.dto.form;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserFormDtoTest {

    @Test
    void isPasswordMatching() {
        UserFormDto userFormDto = new UserFormDto();
        userFormDto.setPassword("password");
        userFormDto.setPasswordForVerification("password");
        assertTrue(userFormDto.isPasswordMatching());
    }

    @Test
    void isPasswordMatchingWithDifferentPassword() {
        UserFormDto userFormDto = new UserFormDto();
        userFormDto.setPassword("password");
        userFormDto.setPasswordForVerification("password2");
        assertFalse(userFormDto.isPasswordMatching());
    }
}