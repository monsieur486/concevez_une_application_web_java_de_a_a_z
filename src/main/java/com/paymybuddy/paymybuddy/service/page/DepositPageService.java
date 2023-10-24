package com.paymybuddy.paymybuddy.service.page;

import com.paymybuddy.paymybuddy.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DepositPageService {

    private final UserService userService;

    public DepositPageService(UserService userService) {
        this.userService = userService;
    }
}
