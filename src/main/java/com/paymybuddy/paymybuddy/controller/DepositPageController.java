package com.paymybuddy.paymybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DepositPageController {

    private static final String ACTIVE_PAGE = "profile";

    @GetMapping("/profile/deposit")
    public String showDepositPage(Model model) {
        model.addAttribute("activePage", ACTIVE_PAGE);
        return "deposit";
    }
}
