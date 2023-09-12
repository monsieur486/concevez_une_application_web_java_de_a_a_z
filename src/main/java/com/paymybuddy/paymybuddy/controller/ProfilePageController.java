package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.entity.User;
import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class ProfilePageController {

    private final UserService userService;

    public ProfilePageController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/profile")
    public String showAdminPage(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        String activePage = "profile";
        model.addAttribute("activePage", activePage);
        model.addAttribute("user", user);
        return "profile";
    }
}
