package com.paymybuddy.paymybuddy.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    Bank bank = new Bank();

    @Test
    void deposit() {
        assertTrue(bank.deposit(2.0, null));
        assertFalse(bank.deposit(3.0, null));
    }

    @Test
    void withdrawal() {
        assertTrue(bank.withdrawal(2.0, null));
        assertFalse(bank.withdrawal(3.0, null));
    }

    @Test
    void isEven() {
        assertTrue(Bank.isEven(2));
        assertFalse(Bank.isEven(3));
    }
}