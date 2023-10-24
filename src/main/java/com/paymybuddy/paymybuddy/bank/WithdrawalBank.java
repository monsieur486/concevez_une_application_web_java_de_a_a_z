package com.paymybuddy.paymybuddy.bank;

public interface WithdrawalBank {

    Boolean withdrawal(Integer amount, WithdrawalInformation withdrawalInformation);
}
