package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.entity.User;
import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class TransferPageController {

    private final UserService userService;

    public TransferPageController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/transfer")
    public String showTransferPage(Model model, Principal principal) {
        String activePage = "transfer";
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("activePage", activePage);
        model.addAttribute("user", user);
        return "transfer";
    }
}
