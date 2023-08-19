package com.paymybuddy.paymybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginPageController {

    @RequestMapping(value = "/login")
    public String showAdminPage(Model model) {
        String activePage = "login";
        model.addAttribute("activePage", activePage);
        return "login";
    }
}
