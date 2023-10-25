package com.paymybuddy.paymybuddy.bank;

public interface WithdrawalBank {

    Boolean withdrawal(Double amount, WithdrawalInformation withdrawalInformation);
}
