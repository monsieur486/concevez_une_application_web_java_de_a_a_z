package com.paymybuddy.paymybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProfilePageController {

    @RequestMapping(value = "/profile")
    public String showAdminPage(Model model) {
        String activePage = "profile";
        model.addAttribute("activePage", activePage);
        return "profile";
    }
}
