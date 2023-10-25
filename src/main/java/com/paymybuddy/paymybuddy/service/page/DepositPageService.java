package com.paymybuddy.paymybuddy.service.page;

import com.paymybuddy.paymybuddy.bank.Bank;
import com.paymybuddy.paymybuddy.bank.DepositInformation;
import com.paymybuddy.paymybuddy.config.ApplicationConfiguration;
import com.paymybuddy.paymybuddy.dto.form.DepositFormDto;
import com.paymybuddy.paymybuddy.dto.page.DepositPageDto;
import com.paymybuddy.paymybuddy.entity.User;
import com.paymybuddy.paymybuddy.service.UserService;
import com.paymybuddy.paymybuddy.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DepositPageService {

    private final UserService userService;

    private final Bank bank;

    public DepositPageService(UserService userService, Bank bank) {
        this.userService = userService;
        this.bank = bank;
    }

    public Boolean depositMoney(String email, Integer amount, DepositInformation depositInformation) {
        User userDB = userService.findByEmail(email);
        Integer balance = userDB.getBalance();
        Integer amountInCents = amount * 100;
        Double fee = amountInCents * ApplicationConfiguration.DEPOSIT_FEE_PERCENTAGE;
        Integer newBalance = balance + (amountInCents - fee.intValue());
        Double realAmount = (amountInCents - fee.intValue()) / 100.0;

        Boolean success = bank.deposit(realAmount, depositInformation);

        if (!success) {
            return false;
        }

        userService.setBalance(userDB, newBalance);
        return true;
    }

    public DepositPageDto renderDepositPage(String principal, DepositFormDto depositForm) {
        DepositPageDto depositPageDto = new DepositPageDto();

        User userDB = userService.findByEmail(principal);
        depositPageDto.setSolde(StringUtil.convertCentsInMoney(userDB.getBalance()));
        if(depositForm == null) {
            depositForm = new DepositFormDto();
        }

        depositPageDto.setDepositForm(depositForm);

        return depositPageDto;
    }
}
