package com.paymybuddy.paymybuddy.utils;

import com.github.javafaker.Faker;

public class FakeData {

    private FakeData() {
    }

    public static String getEmail() {
        Faker faker = new Faker();
        String email = faker.name().firstName()
                + "."
                + faker.name().lastName()
                + faker.number().digits(4)
                + "@gmail.com";
        return email.toLowerCase();
    }
}
