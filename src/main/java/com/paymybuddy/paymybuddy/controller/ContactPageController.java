package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.form.ContactFormDto;
import com.paymybuddy.paymybuddy.service.MessageService;
import com.paymybuddy.paymybuddy.service.UserService;
import com.paymybuddy.paymybuddy.tools.StringUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Objects;

/**
 * Controller for handling contact page related requests.
 */
@Controller
@Slf4j
public class ContactPageController {

    private static final String ACTIVE_PAGE = "contact";

    private final MessageService messageService;
    private final UserService userService;

    /**
     * Constructor for ContactPageController.
     *
     * @param messageService the service for handling messages
     * @param userService the service for handling user related operations
     */
    public ContactPageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    /**
     * Handles GET requests to the contact page.
     *
     * @param model the model to be used in the view
     * @param contactForm the form data from the user
     * @param principal the currently authenticated user
     * @return the name of the view to be rendered
     */
    @GetMapping("/contact")
    public String showContactPage(Model model, ContactFormDto contactForm, Principal principal) {
        render(model, contactForm, principal);
        return "contact";
    }

    /**
     * Handles POST requests to the contact page.
     *
     * @param contactForm the form data from the user
     * @param result the result of the form validation
     * @param principal the currently authenticated user
     * @param model the model to be used in the view
     * @return the name of the view to be rendered
     */
    @PostMapping("/contact")
    public String addMessage(@Valid @ModelAttribute("contactForm") ContactFormDto contactForm,
                             BindingResult result,
                             Principal principal,
                             Model model) {

        render(model, contactForm, principal);

        if (contactForm.getEmail().isEmpty()) {
            result.rejectValue(
                    "email",
                    "",
                    "Email mandatory");
        }

        if (!StringUtil.isValidEmail(contactForm.getEmail())) {
            result.rejectValue(
                    "email",
                    "",
                    "Please enter a valid email address");
        }


        if (contactForm.getContent().isEmpty()) {
            result.rejectValue(
                    "content",
                    "",
                    "Content mandatory");
        }

        if (result.hasErrors()) {
            model.addAttribute("message", contactForm);
            return ACTIVE_PAGE;
        }

        messageService.saveMessage(contactForm);
        return "redirect:/" + ACTIVE_PAGE + "?success";
    }

    /**
     * Helper method to set up the model for the view.
     *
     * @param model the model to be used in the view
     * @param contactForm the form data from the user
     * @param principal the currently authenticated user
     */
    private void render(Model model, ContactFormDto contactForm, Principal principal) {
        if (contactForm == null) contactForm = new ContactFormDto();

        if (principal != null && Objects.equals(contactForm.getEmail(), "")) {
            contactForm.setEmail(principal.getName());
        }

        model.addAttribute("contactForm", contactForm);
        model.addAttribute("activePage", ACTIVE_PAGE);
    }
}
