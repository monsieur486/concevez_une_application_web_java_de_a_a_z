package com.paymybuddy.paymybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WithdrawalPageController {

    private static final String ACTIVE_PAGE = "profile";

    @RequestMapping(value = "/profile/withdrawal")
    public String showWithdrawalPage(Model model) {
        model.addAttribute("activePage", ACTIVE_PAGE);
        return "withdrawal";
    }
}
