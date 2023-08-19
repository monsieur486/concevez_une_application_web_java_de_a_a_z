package com.paymybuddy.paymybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactPageController {

    @GetMapping("/contact")
    public String showAdminPage(Model model) {
        String activePage = "contact";
        model.addAttribute("activePage", activePage);
        return "contact";
    }
}
