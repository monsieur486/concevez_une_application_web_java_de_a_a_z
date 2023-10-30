package com.paymybuddy.paymybuddy.bank;

import org.springframework.stereotype.Component;

@Component
public class Bank implements DepositBank, WithdrawalBank {

    static Boolean isEven(int number) {
        return number % 2 == 0;
    }


    @Override
    public Boolean deposit(Double amount, DepositInformation depositInformation) {
        return isEven(amount.intValue());
    }

    @Override
    public Boolean withdrawal(Double amount, WithdrawalInformation withdrawalInformation) {
        return isEven(amount.intValue());
    }
}
