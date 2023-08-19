package com.paymybuddy.paymybuddy.utils;

public class StringUtil {

    private StringUtil() {
    }

    public static boolean containsAcapitalLetter(String string) {
        return string.matches(".*[A-Z].*");
    }

    public static boolean containsAlowercase(String string) {
        return string.matches(".*[a-z].*");
    }

    public static boolean containsANumber(String string) {
        return string.matches(".*\\d.*");
    }
}
