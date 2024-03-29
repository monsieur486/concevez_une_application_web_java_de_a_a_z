package com.paymybuddy.paymybuddy.service.page;

import com.paymybuddy.paymybuddy.bank.Bank;
import com.paymybuddy.paymybuddy.bank.WithdrawalInformation;
import com.paymybuddy.paymybuddy.config.ApplicationConfiguration;
import com.paymybuddy.paymybuddy.dto.form.WithdrawalFormDto;
import com.paymybuddy.paymybuddy.dto.page.WithdrawalPageDto;
import com.paymybuddy.paymybuddy.entity.User;
import com.paymybuddy.paymybuddy.service.UserService;
import com.paymybuddy.paymybuddy.tools.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WithdrawalPageService {

    private final UserService userService;

    private final Bank bank;

    public WithdrawalPageService(UserService userService, Bank bank) {
        this.userService = userService;
        this.bank = bank;
    }

    public Boolean withdrawalMoney(String email, Double amount, WithdrawalInformation withdrawalInformation) {
        User userDB = userService.findByEmail(email);
        Integer balance = userDB.getBalance();
        int amountInCents = (int) (amount * 100);
        double fee = amountInCents * ApplicationConfiguration.WITHDRAWAL_FEE_PERCENTAGE;
        Double realAmount = (amountInCents - (int) fee) / 100.0;
        Integer newBalance = balance - (amountInCents + (int) fee);

        Boolean success = bank.withdrawal(realAmount, withdrawalInformation);

        if (!success) {
            return false;
        }

        userService.setBalance(userDB, newBalance);
        return true;
    }

    public WithdrawalPageDto renderWithdrawalPage(String userMail, WithdrawalFormDto withdrawalForm) {
        WithdrawalPageDto withdrawalPageDto = new WithdrawalPageDto();

        User userDB = userService.findByEmail(userMail);
        withdrawalPageDto.setSolde(StringUtil.convertCentsInMoney(userDB.getBalance()));

        if (withdrawalForm == null) {
            withdrawalForm = new WithdrawalFormDto();
        }

        withdrawalPageDto.setWithdrawalForm(withdrawalForm);

        return withdrawalPageDto;
    }

    public Boolean balanceIsSufficient(String userEmail, Double amount) {
        User userDB = userService.findByEmail(userEmail);
        Integer balance = userDB.getBalance();
        Integer amountInCents = (int) (amount * 100);

        return balance >= amountInCents;
    }
}
