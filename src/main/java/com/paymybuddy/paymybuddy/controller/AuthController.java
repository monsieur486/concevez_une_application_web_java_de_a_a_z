package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.UserDto;
import com.paymybuddy.paymybuddy.entity.User;
import com.paymybuddy.paymybuddy.service.UserService;
import com.paymybuddy.paymybuddy.utils.StringUtil;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;

    private static final String PASSWORD = "password";

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model){
        User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", "", "There is already an account registered with that email");
        }
        if (!StringUtil.containsAcapitalLetter(user.getPassword())) {
            result.rejectValue(PASSWORD, "", "Password must contain at least one capital letter");
        }
        if (!StringUtil.containsAlowercase(user.getPassword())) {
            result.rejectValue(PASSWORD, "", "Password must contain at least one lower case letter");
        }
        if (!StringUtil.containsANumber(user.getPassword())) {
            result.rejectValue(PASSWORD, "", "Password must contain at least one number");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/register?success";
    }
}