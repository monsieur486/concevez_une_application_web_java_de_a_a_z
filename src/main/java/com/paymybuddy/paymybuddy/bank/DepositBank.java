package com.paymybuddy.paymybuddy.bank;

public interface DepositBank {

    Boolean deposit(Double amount, DepositInformation depositInformation);
}
