package com.paymybuddy.paymybuddy.config;

public class ApplicationConfiguration {

    // Remember me cookie configuration
    public static final int REMEMBER_ME_VALIDITY_SECONDS = 7 * 24 * 60 * 60;

    // Display
    public static final String CURRENCY = "€";
    public static final int NUMBER_OF_CONNECTIONS_TO_DISPLAY = 5;
    public static final int NUMBER_OF_TRANSACTIONS_TO_DISPLAY = 5;

    // Password configuration
    public static final int MINIMUM_PASSWORD_LENGTH = 8;
    public static final int MAXIMUM_PASSWORD_LENGTH = 50;
    public static final boolean PASSWORD_MUST_CONTAIN_DIGIT = true;
    public static final boolean PASSWORD_MUST_CONTAIN_LOWERCASE = true;
    public static final boolean PASSWORD_MUST_CONTAIN_UPPERCASE = true;

    // fee configuration
    public static final double TRANSACTION_FEE_PERCENTAGE = 0.005;
    public static final int MINIMUM_AMOUNT_TRANSACTION = 5;
    public static final int MAXIMUM_AMOUNT_TRANSACTION = 1000;
    public static final int MINIMUM_AMOUNT_DEPOSIT = 10;
    public static final double DEPOSIT_FEE_PERCENTAGE = 0.000;
    public static final int MAXIMUM_AMOUNT_DEPOSIT = 1000;
    public static final int MINIMUM_AMOUNT_WITHDRAWAL = 0;
    public static final int MAXIMUM_AMOUNT_WITHDRAWAL = 1000;
    public static final double WITHDRAWAL_FEE_PERCENTAGE = 0.000;
    private ApplicationConfiguration() {
    }
}
