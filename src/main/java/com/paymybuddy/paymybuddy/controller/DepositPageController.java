package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.config.ApplicationConfiguration;
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

/**
 * Controller for handling deposit related requests.
 */
@Controller
public class DepositPageController {

    private static final String ACTIVE_PAGE = "profile";

    private final DepositPageService depositPageService;

    /**
     * Constructor for DepositPageController.
     *
     * @param depositPageService the service to handle deposit page related operations
     */
    public DepositPageController(DepositPageService depositPageService) {
        this.depositPageService = depositPageService;
    }

    /**
     * Handles GET requests to show the deposit page.
     *
     * @param model the model to add attributes to for the view
     * @param principal the currently authenticated user
     * @param depositForm the form data for the deposit
     * @return the name of the view to render
     */
    @GetMapping("/profile/deposit")
    public String showDepositPage(Model model, Principal principal, DepositFormDto depositForm) {
        model.addAttribute("activePage", ACTIVE_PAGE);

        DepositPageDto depositPageDto = depositPageService.renderDepositPage(principal.getName(), depositForm);
        model.addAttribute("solde", depositPageDto.getSolde());
        model.addAttribute("depositForm", depositPageDto.getDepositForm());

        return "deposit";
    }

    /**
     * Handles POST requests to deposit an amount.
     *
     * @param depositForm the form data for the deposit
     * @param model the model to add attributes to for the view
     * @param principal the currently authenticated user
     * @param result the binding result which can contain form validation errors
     * @return the name of the view to render or a redirect view if the deposit was successful
     */
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

        if (depositForm.getAmount() == null) {
            result.rejectValue(
                    "amount",
                    "",
                    "Deposit mandatory");
            return "deposit";
        }

        if (depositForm.getAmount() < ApplicationConfiguration.MINIMUM_AMOUNT_DEPOSIT) {
            result.rejectValue(
                    "amount",
                    "",
                    "Minimum deposit " + ApplicationConfiguration.MINIMUM_AMOUNT_DEPOSIT + "€");
        }

        if (depositForm.getAmount() > ApplicationConfiguration.MAXIMUM_AMOUNT_DEPOSIT) {
            result.rejectValue(
                    "amount",
                    "",
                    "Maximum deposit " + ApplicationConfiguration.MAXIMUM_AMOUNT_DEPOSIT + "€");
        }

        if (result.hasErrors()) {
            return "deposit";
        }

        Boolean success = depositPageService.depositMoney(
                principal.getName(),
                depositForm.getAmount(),
                null);

        if (success) {
            return "redirect:/profile/deposit?success";
        } else {
            return "redirect:/profile/deposit?error";
        }
    }
}
