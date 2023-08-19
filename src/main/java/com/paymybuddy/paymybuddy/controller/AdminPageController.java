package com.paymybuddy.paymybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {

    @GetMapping("/admin")
    public String showAdminPage(Model model) {
        String activePage = "admin";
        model.addAttribute("activePage", activePage);
        return "admin";
    }
}
