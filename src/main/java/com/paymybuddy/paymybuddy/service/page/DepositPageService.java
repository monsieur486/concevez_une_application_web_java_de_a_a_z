package com.paymybuddy.paymybuddy.service.page;

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

    public DepositPageService(UserService userService) {
        this.userService = userService;
    }

    public void depositMoney(String email, Integer amount) {
        User userDB = userService.findByEmail(email);
        Integer balance = userDB.getBalance();
        Integer amountInCents = amount * 100;
        Double fee = amountInCents * ApplicationConfiguration.DEPOSIT_FEE_PERCENTAGE;
        Integer newBalance = balance + amount - fee.intValue();

        userService.setBalance(userDB, newBalance);
    }

    public DepositPageDto renderDepositPage(String principal, DepositFormDto depositFormDto) {
        DepositPageDto depositPageDto = new DepositPageDto();

        User userDB = userService.findByEmail(principal);
        depositPageDto.setEmail(userDB.getEmail());
        depositPageDto.setSolde(StringUtil.getMoney(userDB.getBalance()));
        if(depositFormDto != null) {
            DepositFormDto depositFormDto1 = new DepositFormDto();
        }
        depositPageDto.setDepositForm(depositFormDto);

        return depositPageDto;
    }
}
