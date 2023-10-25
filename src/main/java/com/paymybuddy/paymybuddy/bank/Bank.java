package com.paymybuddy.paymybuddy.bank;

import com.paymybuddy.paymybuddy.utils.StringUtil;
import org.springframework.stereotype.Component;

@Component
public class Bank implements DepositBank, WithdrawalBank {


    @Override
    public Boolean deposit(Double amount, DepositInformation depositInformation) {
        if(StringUtil.isEven(amount.intValue())){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean withdrawal(Double amount, WithdrawalInformation withdrawalInformation) {
        if(StringUtil.isEven(amount.intValue())){
            return true;
        } else {
            return false;
        }
    }
}
