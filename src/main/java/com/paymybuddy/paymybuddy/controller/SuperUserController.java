package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SuperUserController {

    private final UserService userService;

    @Value("${TOKEN_SECRET}")
    private String tokenSecret;

    public SuperUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/superuser")
    public String createSuperUser(@RequestParam String token) {
        if(token.equals(tokenSecret)){
            userService.createSuperUser();
        }

        return "redirect:/";
    }
}
