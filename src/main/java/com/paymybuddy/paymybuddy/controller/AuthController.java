package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.config.ApplicationConfiguration;
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
        String activePage = "register";
        model.addAttribute("activePage", activePage);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model){
        User existing = userService.findByEmail(user.getEmail());
        String activePage = "register";
        model.addAttribute("activePage", activePage);
        if (existing != null) {
            result.rejectValue(
                    "email",
                    "",
                    "There is already an account registered with that email");
        }

        if (ApplicationConfiguration.PASSWORD_MUST_CONTAIN_UPPERCASE
                && (!StringUtil.containsCapitalLetter(user.getPassword()))) {
            result.rejectValue(
                    PASSWORD,
                    "",
                    "Password must contain at least one capital letter");
        }

        if (ApplicationConfiguration.PASSWORD_MUST_CONTAIN_LOWERCASE
                && (!StringUtil.containsLowercaseLetter(user.getPassword()))) {
            result.rejectValue(
                    PASSWORD,
                    "",
                    "Password must contain at least one lower case letter");
        }

        if (ApplicationConfiguration.PASSWORD_MUST_CONTAIN_DIGIT
                && (!StringUtil.containsDigit(user.getPassword()))) {
            result.rejectValue(
                    PASSWORD,
                    "",
                    "Password must contain at least one digit");
        }

        if (user.getPassword().length() < ApplicationConfiguration.MINIMUM_PASSWORD_LENGTH) {
            result.rejectValue(
                    PASSWORD,
                    "",
                    "Password must be at least "
                            + ApplicationConfiguration.MINIMUM_PASSWORD_LENGTH
                            + " characters long and less than "
                            + ApplicationConfiguration.MAXIMUM_PASSWORD_LENGTH
                            + " characters long");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/register?success";
    }
}
