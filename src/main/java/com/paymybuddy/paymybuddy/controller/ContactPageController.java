package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.MessageDto;
import com.paymybuddy.paymybuddy.entity.Message;
import com.paymybuddy.paymybuddy.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactPageController {

    private final MessageService messageService;

    public ContactPageController(MessageService messageService) {
        this.messageService = messageService;
    }


    @GetMapping("/contact")
    public String showAdminPage(Model model) {
        String activePage = "contact";
        model.addAttribute("activePage", activePage);
        return "contact";
    }

    @PostMapping("/contact")
    public String addMessage(@Valid @ModelAttribute("message")MessageDto message,
                             BindingResult result,
                             Model model) {
        Message newMessage = messageService.saveMessage(message);
        String activePage = "contact";
        model.addAttribute("activePage", activePage);
        if (newMessage != null) {
            result.rejectValue(
                    "email",
                    "",
                    "Email must be non-null");
        }
        return "redirect:/" + activePage + "?success";
    }
}
