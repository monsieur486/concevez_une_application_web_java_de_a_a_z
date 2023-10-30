package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.form.ContactFormDto;
import com.paymybuddy.paymybuddy.entity.User;
import com.paymybuddy.paymybuddy.service.MessageService;
import com.paymybuddy.paymybuddy.service.UserService;
import com.paymybuddy.paymybuddy.utils.StringUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@Slf4j
public class ContactPageController {

    private static final String ACTIVE_PAGE = "contact";

    private final MessageService messageService;
    private final UserService userService;

    public ContactPageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }


    @GetMapping("/contact")
    public String showContactPage(Model model, ContactFormDto contactForm, Principal principal) {
        render(model, contactForm, principal);
        return "contact";
    }

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

    private void render(Model model, ContactFormDto contactForm, Principal principal) {
        if(contactForm == null) contactForm = new ContactFormDto();

        if(principal != null){
            User userConnected = userService.findByEmail(principal.getName());
            if(userConnected != null){
                if(contactForm.getEmail() == null) contactForm.setEmail(userConnected.getEmail());
            }
        }

        model.addAttribute("contactForm", contactForm);
        model.addAttribute("activePage", ACTIVE_PAGE);
    }
}
