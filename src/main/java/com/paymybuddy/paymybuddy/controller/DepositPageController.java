package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.config.ApplicationConfiguration;
import com.paymybuddy.paymybuddy.dto.form.ConnectionFormDto;
import com.paymybuddy.paymybuddy.dto.form.DepositFormDto;
import com.paymybuddy.paymybuddy.dto.page.DepositPageDto;
import com.paymybuddy.paymybuddy.service.page.DepositPageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class DepositPageController {

    private static final String ACTIVE_PAGE = "profile";

    private final DepositPageService depositPageService;

    public DepositPageController(DepositPageService depositPageService) {
        this.depositPageService = depositPageService;
    }

    @GetMapping("/profile/deposit")
    public String showDepositPage(Model model, Principal principal, DepositFormDto depositForm) {
        model.addAttribute("activePage", ACTIVE_PAGE);

        DepositPageDto depositPageDto = depositPageService.renderDepositPage(principal.getName(), depositForm);
        model.addAttribute("solde", depositPageDto.getSolde());
        model.addAttribute("depositForm", depositPageDto.getDepositForm());

        return "deposit";
    }

    @PostMapping("/profile/deposit")
    public String depositAmount(
            @ModelAttribute("depositForm") DepositFormDto depositForm,
            Model model,
            Principal principal,
            BindingResult result
    ) {
        model.addAttribute("activePage", ACTIVE_PAGE);

        DepositPageDto depositPageDto = depositPageService.renderDepositPage(principal.getName(), depositForm);
        model.addAttribute("solde", depositPageDto.getSolde());
        model.addAttribute("depositForm", depositPageDto.getDepositForm());

        if (depositForm.getAmount() < ApplicationConfiguration.MINIMUM_AMOUNT_DEPOSIT) {
            result.rejectValue(
                    "amount",
                    "",
                    "Minimum amount is " + ApplicationConfiguration.MINIMUM_AMOUNT_DEPOSIT + "€");
        }

        if (result.hasErrors()) {
            return "deposit";
        }

        depositPageService.depositMoney(principal.getName(), depositForm.getAmount());

        return "redirect:/" + ACTIVE_PAGE;
    }
}
