package com.paymybuddy.paymybuddy.config;

/**
 * This class contains the configuration constants for the application.
 * It includes settings for remember me cookie, display, password, and transaction fees.
 */
public class ApplicationConfiguration {

    // The validity period for the "Remember me" cookie in seconds. Set to one week.
    public static final int REMEMBER_ME_VALIDITY_SECONDS = 7 * 24 * 60 * 60;

    // The currency symbol to be used in the application.
    public static final String CURRENCY = "€";

    // The number of connections to display in the user interface.
    public static final int NUMBER_OF_CONNECTIONS_TO_DISPLAY = 5;

    // The number of transactions to display in the user interface.
    public static final int NUMBER_OF_TRANSACTIONS_TO_DISPLAY = 5;

    // The minimum length for a user's password.
    public static final int MINIMUM_PASSWORD_LENGTH = 8;

    // The maximum length for a user's password.
    public static final int MAXIMUM_PASSWORD_LENGTH = 50;

    // A flag indicating whether a password must contain a digit.
    public static final boolean PASSWORD_MUST_CONTAIN_DIGIT = true;

    // A flag indicating whether a password must contain a lowercase letter.
    public static final boolean PASSWORD_MUST_CONTAIN_LOWERCASE = true;

    // A flag indicating whether a password must contain an uppercase letter.
    public static final boolean PASSWORD_MUST_CONTAIN_UPPERCASE = true;

    // The percentage of the transaction amount that will be charged as a fee.
    public static final double TRANSACTION_FEE_PERCENTAGE = 0.005;

    // The minimum amount for a transaction.
    public static final int MINIMUM_AMOUNT_TRANSACTION = 5;

    // The maximum amount for a transaction.
    public static final int MAXIMUM_AMOUNT_TRANSACTION = 1000;

    // The minimum amount for a deposit.
    public static final int MINIMUM_AMOUNT_DEPOSIT = 10;

    // The percentage of the deposit amount that will be charged as a fee.
    public static final double DEPOSIT_FEE_PERCENTAGE = 0.000;

    // The maximum amount for a deposit.
    public static final int MAXIMUM_AMOUNT_DEPOSIT = 1000;

    // The minimum amount for a withdrawal.
    public static final int MINIMUM_AMOUNT_WITHDRAWAL = 0;

    // The maximum amount for a withdrawal.
    public static final int MAXIMUM_AMOUNT_WITHDRAWAL = 1000;

    // The percentage of the withdrawal amount that will be charged as a fee.
    public static final double WITHDRAWAL_FEE_PERCENTAGE = 0.000;

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private ApplicationConfiguration() {
    }
}
