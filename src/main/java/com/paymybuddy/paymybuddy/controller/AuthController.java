package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.config.ApplicationConfiguration;
import com.paymybuddy.paymybuddy.dto.form.UserFormDto;
import com.paymybuddy.paymybuddy.entity.User;
import com.paymybuddy.paymybuddy.service.UserService;
import com.paymybuddy.paymybuddy.tools.StringUtil;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller for handling user authentication.
 */
@Controller
public class AuthController {

    private static final String PASSWORD = "password";
    private static final String ACTIVE_PAGE = "register";
    private final UserService userService;

    /**
     * Constructor for the AuthController.
     *
     * @param userService The service to handle user-related operations.
     */
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Handles GET requests to the /register endpoint.
     * Shows the registration form to the user.
     *
     * @param model The model to add attributes to.
     * @return The name of the view to render.
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserFormDto user = new UserFormDto();
        model.addAttribute("user", user);
        model.addAttribute("activePage", ACTIVE_PAGE);
        return ACTIVE_PAGE;
    }

    /**
     * Handles POST requests to the /register/save endpoint.
     * Validates the user input and saves the new user if the input is valid.
     *
     * @param user   The user form data.
     * @param result The binding result containing validation errors.
     * @param model  The model to add attributes to.
     * @return The name of the view to render.
     */
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserFormDto user,
                               BindingResult result,
                               Model model) {
        User existing = userService.findByEmail(user.getEmail());
        model.addAttribute("activePage", ACTIVE_PAGE);
        if (existing != null) {
            result.rejectValue(
                    "email",
                    "",
                    "There is already an account registered with that email");
        }

        if (!StringUtil.isValidEmail(user.getEmail())) {
            result.rejectValue(
                    "email",
                    "",
                    "Please enter a valid email address");
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

        if (!user.isPasswordMatching()) {
            result.rejectValue(
                    "passwordForVerification",
                    "",
                    "Password and password verification must match");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return ACTIVE_PAGE;
        }
        userService.saveUser(user);
        return "redirect:/" + ACTIVE_PAGE + "?success";
    }
}
