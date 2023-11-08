package com.paymybuddy.paymybuddy.service.page;

import com.paymybuddy.paymybuddy.bank.Bank;
import com.paymybuddy.paymybuddy.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DepositPageServiceTest {

    @InjectMocks
    DepositPageService service;

    @Mock
    UserService dao;

    @Mock
    Bank bank;

    @Test
    void depositMoney() {
    }

    @Test
    void renderDepositPage() {
    }
}