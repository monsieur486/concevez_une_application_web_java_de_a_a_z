package com.paymybuddy.paymybuddy.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {



    @Test
    void containsCapitalLetter() {
        assertTrue(StringUtil.containsCapitalLetter("pasSword"));
        assertFalse(StringUtil.containsCapitalLetter("password"));
    }

    @Test
    void containsLowercaseLetter() {
        assertTrue(StringUtil.containsLowercaseLetter("pasSword"));
        assertFalse(StringUtil.containsLowercaseLetter("PASSWORD"));
    }

    @Test
    void containsDigit() {
        assertTrue(StringUtil.containsDigit("pasSword1"));
        assertFalse(StringUtil.containsDigit("pasSword"));
    }

    @Test
    void containsSpecialCharacter() {
        assertTrue(StringUtil.containsSpecialCharacter("pasSword1!"));
        assertFalse(StringUtil.containsSpecialCharacter("pasSword1"));
    }

    @Test
    void isValidEmail() {
        assertTrue(StringUtil.isValidEmail("toto@gmail.fr"));
        assertFalse(StringUtil.isValidEmail("toto"));
        assertFalse(StringUtil.isValidEmail("toto@"));
        assertFalse(StringUtil.isValidEmail("toto@fr"));
        assertFalse(StringUtil.isValidEmail("@toto"));
        assertFalse(StringUtil.isValidEmail("@toto.fr"));
    }

    @Test
    void convertCentsInMoney() {
    }
}