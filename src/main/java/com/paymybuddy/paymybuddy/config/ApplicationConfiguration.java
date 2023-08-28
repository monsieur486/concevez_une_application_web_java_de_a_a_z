package com.paymybuddy.paymybuddy.config;

public class ApplicationConfiguration {

    private ApplicationConfiguration() {}


    // Remember me cookie configuration
    public static final int REMEMBER_ME_VALIDITY_SECONDS = 7 * 24 * 60 * 60;


    // Password configuration
    public static final int MINIMUM_PASSWORD_LENGTH = 8;
    public static final int MAXIMUM_PASSWORD_LENGTH = 50;
    public static final boolean PASSWORD_MUST_CONTAIN_DIGIT = true;
    public static final boolean PASSWORD_MUST_CONTAIN_LOWERCASE = true;
    public static final boolean PASSWORD_MUST_CONTAIN_UPPERCASE = true;

    // fee configuration
    public static final double FEE_PERCENTAGE = 0.005;
    public static final String CURRENCY_SYMBOL = "€";
    public static final int MINIMUM_AMOUNT_TRANSACTION = 5;
    public static final int MAXIMUM_AMOUNT_TRANSACTION = 1000;
    public static final int MINIMUM_AMOUNT_PAYMENT = 15;
    public static final int MAXIMUM_AMOUNT_PAYMENT = 1000;
}
