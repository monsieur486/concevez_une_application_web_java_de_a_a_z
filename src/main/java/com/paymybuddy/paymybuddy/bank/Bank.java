package com.paymybuddy.paymybuddy.bank;

import com.paymybuddy.paymybuddy.utils.StringUtil;
import org.springframework.stereotype.Component;

@Component
public class Bank implements DepositBank, WithdrawalBank {

    private static Boolean isEven(int number) {
        return number % 2 == 0;
    }


    @Override
    public Boolean deposit(Double amount, DepositInformation depositInformation) {
        if(isEven(amount.intValue())){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean withdrawal(Double amount, WithdrawalInformation withdrawalInformation) {
        if(isEven(amount.intValue())){
            return true;
        } else {
            return false;
        }
    }
}
