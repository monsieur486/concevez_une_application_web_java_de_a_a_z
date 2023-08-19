package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SuperUserController {

    private final UserService userService;

    public SuperUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/superuser")
    public String createSuperUser() {
        userService.createSuperUser();
        return "redirect:/";
    }
}
