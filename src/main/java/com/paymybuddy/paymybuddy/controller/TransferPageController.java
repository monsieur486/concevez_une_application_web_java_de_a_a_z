package com.paymybuddy.paymybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TransferPageController {

    @RequestMapping(value = "/transfer")
    public String showTransferPage(Model model) {
        String activePage = "transfer";
        model.addAttribute("activePage", activePage);
        return "transfer";
    }
}
