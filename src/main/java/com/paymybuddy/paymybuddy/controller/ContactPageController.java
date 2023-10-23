package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.MessageDto;
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
    public String showAdminPage(Model model) {
        MessageDto message = new MessageDto();
        model.addAttribute("message", message);
        model.addAttribute("activePage", ACTIVE_PAGE);
        return "contact";
    }

    @PostMapping("/contact/send")
    public String addMessage(@Valid @ModelAttribute("message")MessageDto message,
                             BindingResult result,
                             Model model) {

        model.addAttribute("activePage", ACTIVE_PAGE);

        if (message.getEmail().isEmpty()) {
            result.rejectValue(
                    "email",
                    "",
                    "Email mandatory");
        }

        if (StringUtil.isValidEmail(message.getEmail())) {
            result.rejectValue(
                    "email",
                    "",
                    "Please enter a valid email address");
        }


        if (message.getContent().isEmpty()) {
            result.rejectValue(
                    "content",
                    "",
                    "Content mandatory");
        }

        if (result.hasErrors()) {
            model.addAttribute("message", message);
            return ACTIVE_PAGE;
        }

        messageService.saveMessage(message);
        return "redirect:/" + ACTIVE_PAGE + "?success";
    }
}
