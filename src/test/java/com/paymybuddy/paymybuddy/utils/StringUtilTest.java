package com.paymybuddy.paymybuddy.utils;

import com.paymybuddy.paymybuddy.config.ApplicationConfiguration;
import com.paymybuddy.paymybuddy.tools.StringUtil;
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
        assertTrue(StringUtil.containsLowercaseLetter("PASsWORD"));
        assertFalse(StringUtil.containsLowercaseLetter("PASSWORD"));
    }

    @Test
    void containsDigit() {
        assertTrue(StringUtil.containsDigit("pasSword1"));
        assertFalse(StringUtil.containsDigit("pasSword"));
    }

    @Test
    void containsSpecialCharacter() {
        assertTrue(StringUtil.containsSpecialCharacter("pasSword!"));
        assertFalse(StringUtil.containsSpecialCharacter("pasSword"));
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
        assertEquals("1,00 " + ApplicationConfiguration.CURRENCY, StringUtil.convertCentsInMoney(100));
        assertEquals("2,50 " + ApplicationConfiguration.CURRENCY, StringUtil.convertCentsInMoney(250));
        assertEquals("3,05 " + ApplicationConfiguration.CURRENCY, StringUtil.convertCentsInMoney(305));
    }
}