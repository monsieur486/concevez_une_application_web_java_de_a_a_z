package com.paymybuddy.paymybuddy.bank;

import com.paymybuddy.paymybuddy.utils.StringUtil;
import org.springframework.stereotype.Component;

@Component
public class FakeBank implements DepositBank, WithdrawalBank{


    @Override
    public Boolean deposit(Integer amount, DepositInformation depositInformation) {
        if(StringUtil.isEven(amount)){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean withdrawal(Integer amount, WithdrawalInformation withdrawalInformation) {
        if(StringUtil.isEven(amount)){
            return true;
        } else {
            return false;
        }
    }
}
