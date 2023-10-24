package com.paymybuddy.paymybuddy.service.page;

import com.paymybuddy.paymybuddy.config.ApplicationConfiguration;
import com.paymybuddy.paymybuddy.dto.form.DepositFormDto;
import com.paymybuddy.paymybuddy.dto.page.DepositPageDto;
import com.paymybuddy.paymybuddy.entity.User;
import com.paymybuddy.paymybuddy.service.UserService;
import com.paymybuddy.paymybuddy.utils.StringUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DepositPageService {

    private final UserService userService;

    public DepositPageService(UserService userService) {
        this.userService = userService;
    }

    public void depositMoney(String email, Integer amount) {
        User userDB = userService.findByEmail(email);
        Integer balance = userDB.getBalance();
        Integer amountInCents = amount * 100;
        Double fee = amountInCents * ApplicationConfiguration.DEPOSIT_FEE_PERCENTAGE;
        Integer newBalance = balance + (amountInCents - fee.intValue());

        userService.setBalance(userDB, newBalance);
    }

    public DepositPageDto renderDepositPage(String principal, DepositFormDto depositForm) {
        DepositPageDto depositPageDto = new DepositPageDto();

        User userDB = userService.findByEmail(principal);
        depositPageDto.setSolde(StringUtil.getMoney(userDB.getBalance()));
        if(depositForm == null) {
            depositForm = new DepositFormDto();
        }
        if(depositForm.getAmount() == null) {
            depositForm.setAmount(0);
        }

        depositPageDto.setDepositForm(depositForm);

        log.warn("DepositPageDto: {}", depositPageDto.toString());

        return depositPageDto;
    }
}
