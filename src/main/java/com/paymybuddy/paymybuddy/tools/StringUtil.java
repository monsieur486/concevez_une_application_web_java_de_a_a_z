package com.paymybuddy.paymybuddy.tools;

import com.paymybuddy.paymybuddy.config.ApplicationConfiguration;
import org.springframework.stereotype.Component;

/**
 * Utility class for string operations.
 */
@Component
public class StringUtil {

    private StringUtil() {
    }

    /**
     * Checks if the given string contains a capital letter.
     *
     * @param string the string to check
     * @return true if the string contains a capital letter, false otherwise
     */
    public static boolean containsCapitalLetter(String string) {
        return string.matches(".*[A-Z].*");
    }

    /**
     * Checks if the given string contains a lowercase letter.
     *
     * @param string the string to check
     * @return true if the string contains a lowercase letter, false otherwise
     */
    public static boolean containsLowercaseLetter(String string) {
        return string.matches(".*[a-z].*");
    }

    /**
     * Checks if the given string contains a digit.
     *
     * @param string the string to check
     * @return true if the string contains a digit, false otherwise
     */
    public static boolean containsDigit(String string) {
        return string.matches(".*\\d.*");
    }

    /**
     * Checks if the given string contains a special character.
     *
     * @param string the string to check
     * @return true if the string contains a special character, false otherwise
     */
    public static boolean containsSpecialCharacter(String string) {
        return string.matches(".*[^a-zA-Z0-9].*");
    }

    /**
     * Checks if the given string is a valid email.
     *
     * @param string the string to check
     * @return true if the string is a valid email, false otherwise
     */
    public static boolean isValidEmail(String string) {
        return string.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    /**
     * Converts the given balance in cents to a formatted string in the application's currency.
     *
     * @param solde the balance in cents
     * @return the formatted string representing the balance in the application's currency
     */
    public static String convertCentsInMoney(Integer solde) {
        return String.format("%.2f", solde / 100.0) + " " + ApplicationConfiguration.CURRENCY;
    }
}
