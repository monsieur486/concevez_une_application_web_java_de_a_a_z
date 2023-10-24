package com.paymybuddy.paymybuddy.utils;

import org.springframework.stereotype.Component;

@Component
public class StringUtil {

    private StringUtil() {
    }

    public static boolean containsCapitalLetter(String string) {
        return string.matches(".*[A-Z].*");
    }

    public static boolean containsLowercaseLetter(String string) {
        return string.matches(".*[a-z].*");
    }

    public static boolean containsDigit(String string) {
        return string.matches(".*\\d.*");
    }

    public static boolean containsSpecialCharacter(String string) {
        return string.matches(".*[^a-zA-Z0-9].*");
    }

    public static boolean isValidEmail(String string) {
        return !string.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    public static String getMoney(Integer solde) {
        String result;
        result = String.format("%.2f", solde / 100.0);
        return result + " €";
    }
}
