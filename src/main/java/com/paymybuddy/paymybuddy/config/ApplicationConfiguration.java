package com.paymybuddy.paymybuddy.config;

public class ApplicationConfiguration {

    private ApplicationConfiguration() {}
    public static final int REMEMBER_ME_VALIDITY_SECONDS = 7 * 24 * 60 * 60;
    public static final int MINIMUM_PASSWORD_LENGTH = 8;
    public static final int MAXIMUM_PASSWORD_LENGTH = 50;
    public static final boolean PASSWORD_MUST_CONTAIN_DIGIT = true;
    public static final boolean PASSWORD_MUST_CONTAIN_LOWERCASE = true;
    public static final boolean PASSWORD_MUST_CONTAIN_UPPERCASE = true;
}
