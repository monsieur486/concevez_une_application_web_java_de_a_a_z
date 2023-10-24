package com.paymybuddy.paymybuddy.bank;

public interface DepositBank {

    Boolean deposit(Integer amount, DepositInformation depositInformation);
}
