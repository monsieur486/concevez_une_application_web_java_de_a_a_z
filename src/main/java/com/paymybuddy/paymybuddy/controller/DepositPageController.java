package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.form.DepositFormDto;
import com.paymybuddy.paymybuddy.dto.page.DepositPageDto;
import com.paymybuddy.paymybuddy.service.page.DepositPageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class DepositPageController {

    private static final String ACTIVE_PAGE = "profile";

    private final DepositPageService depositPageService;

    public DepositPageController(DepositPageService depositPageService) {
        this.depositPageService = depositPageService;
    }

    @GetMapping("/profile/deposit")
    public String showDepositPage(Model model, Principal principal, DepositFormDto depositFormDto) {
        model.addAttribute("activePage", ACTIVE_PAGE);

        DepositPageDto depositPageDto = depositPageService.renderDepositPage(principal.getName(), depositFormDto);
        model.addAttribute("mail", depositPageDto.getEmail());
        model.addAttribute("solde", depositPageDto.getSolde());
        model.addAttribute("depositForm", depositPageDto.getDepositForm());

        return "deposit";
    }
}
