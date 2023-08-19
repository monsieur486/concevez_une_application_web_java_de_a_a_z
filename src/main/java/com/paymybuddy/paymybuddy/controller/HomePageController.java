package com.paymybuddy.paymybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

    @GetMapping("/")
    public String showHomePage(Model model) {
        String activePage = "home";
        model.addAttribute("activePage", activePage);
        return "index";
    }
}
