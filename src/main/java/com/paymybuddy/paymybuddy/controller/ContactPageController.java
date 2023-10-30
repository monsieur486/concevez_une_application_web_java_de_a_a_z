package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.form.ContactFormDto;
import com.paymybuddy.paymybuddy.service.MessageService;
import com.paymybuddy.paymybuddy.utils.StringUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class ContactPageController {

    private static final String ACTIVE_PAGE = "contact";

    private final MessageService messageService;

    public ContactPageController(MessageService messageService) {
        this.messageService = messageService;
    }


    @GetMapping("/contact")
    public String showContactPage(Model model, ContactFormDto contactForm) {
        render(model, contactForm);
        return "contact";
    }

    @PostMapping("/contact")
    public String addMessage(@Valid @ModelAttribute("contactForm") ContactFormDto contactForm,
                             BindingResult result,
                             Model model) {

        render(model, contactForm);

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

    private void render(Model model, ContactFormDto contactForm) {
        if(contactForm == null) contactForm = new ContactFormDto();
        model.addAttribute("contactForm", contactForm);
        model.addAttribute("activePage", ACTIVE_PAGE);
    }
}
