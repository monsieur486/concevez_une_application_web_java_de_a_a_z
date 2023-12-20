package com.paymybuddy.paymybuddy.service.page;

import com.paymybuddy.paymybuddy.bank.Bank;
import com.paymybuddy.paymybuddy.bank.DepositInformation;
import com.paymybuddy.paymybuddy.config.ApplicationConfiguration;
import com.paymybuddy.paymybuddy.dto.form.DepositFormDto;
import com.paymybuddy.paymybuddy.dto.page.DepositPageDto;
import com.paymybuddy.paymybuddy.entity.User;
import com.paymybuddy.paymybuddy.service.UserService;
import com.paymybuddy.paymybuddy.tools.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service class for handling operations related to the deposit page.
 */
@Service
@Slf4j
public class DepositPageService {

    private final UserService userService;
    private final Bank bank;

    /**
     * Constructor for the DepositPageService.
     *
     * @param userService the user service
     * @param bank        the bank service
     */
    public DepositPageService(UserService userService, Bank bank) {
        this.userService = userService;
        this.bank = bank;
    }

    /**
     * Method to deposit money into a user's account.
     *
     * @param email              the email of the user
     * @param amount             the amount to be deposited
     * @param depositInformation the deposit information
     * @return true if the deposit was successful, false otherwise
     */
    public Boolean depositMoney(String email, Integer amount, DepositInformation depositInformation) {
        User userDB = userService.findByEmail(email);
        Integer balance = userDB.getBalance();
        int amountInCents = amount * 100;
        double fee = amountInCents * ApplicationConfiguration.DEPOSIT_FEE_PERCENTAGE;
        Integer newBalance = balance + (amountInCents - (int) fee);
        Double realAmount = (amountInCents - (int) fee) / 100.0;

        Boolean success = bank.deposit(realAmount, depositInformation);

        if (!success) {
            return false;
        }

        userService.setBalance(userDB, newBalance);
        return true;
    }

    /**
     * Method to render the deposit page.
     *
     * @param principal   the principal user
     * @param depositForm the deposit form
     * @return the deposit page DTO
     */
    public DepositPageDto renderDepositPage(String principal, DepositFormDto depositForm) {
        DepositPageDto depositPageDto = new DepositPageDto();

        User userDB = userService.findByEmail(principal);
        depositPageDto.setSolde(StringUtil.convertCentsInMoney(userDB.getBalance()));
        if (depositForm == null) {
            depositForm = new DepositFormDto();
        }

        depositPageDto.setDepositForm(depositForm);

        return depositPageDto;
    }
}