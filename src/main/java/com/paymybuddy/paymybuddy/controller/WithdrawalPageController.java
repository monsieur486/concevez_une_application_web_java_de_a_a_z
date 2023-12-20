package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.config.ApplicationConfiguration;
import com.paymybuddy.paymybuddy.dto.form.WithdrawalFormDto;
import com.paymybuddy.paymybuddy.dto.page.WithdrawalPageDto;
import com.paymybuddy.paymybuddy.service.page.WithdrawalPageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

/**
 * Controller for handling withdrawal related requests.
 */
@Controller
public class WithdrawalPageController {

    private static final String ACTIVE_PAGE = "profile";

    private final WithdrawalPageService withdrawalPageService;

    /**
     * Constructor for the WithdrawalPageController.
     *
     * @param withdrawalPageService the service to handle withdrawal related operations
     */
    public WithdrawalPageController(WithdrawalPageService withdrawalPageService) {
        this.withdrawalPageService = withdrawalPageService;
    }

    /**
     * Handles GET requests to show the withdrawal page.
     *
     * @param model the model to add attributes to for rendering in the view
     * @param principal the currently authenticated user
     * @param withdrawalForm the form data for the withdrawal
     * @return the name of the view to render
     */
    @GetMapping(value = "/profile/withdrawal")
    public String showWithdrawalPage(Model model, Principal principal, WithdrawalFormDto withdrawalForm) {
        model.addAttribute("activePage", ACTIVE_PAGE);
        WithdrawalPageDto withdrawalPageDto = withdrawalPageService.renderWithdrawalPage(principal.getName(), withdrawalForm);
        model.addAttribute("solde", withdrawalPageDto.getSolde());
        model.addAttribute("withdrawalForm", withdrawalPageDto.getWithdrawalForm());

        return "withdrawal";
    }

    /**
     * Handles POST requests to process a withdrawal.
     *
     * @param withdrawalForm the form data for the withdrawal
     * @param model the model to add attributes to for rendering in the view
     * @param principal the currently authenticated user
     * @param result the BindingResult that holds the result of the validation and binding and allows for a Validator to be applied
     * @return the name of the view to render
     */
    @PostMapping(value = "/profile/withdrawal")
    public String withdrawalAmount(@ModelAttribute("withdrawalForm") WithdrawalFormDto withdrawalForm,
                                   Model model,
                                   Principal principal,
                                   BindingResult result) {
        model.addAttribute("activePage", ACTIVE_PAGE);
        WithdrawalPageDto withdrawalPageDto = withdrawalPageService.renderWithdrawalPage(principal.getName(), withdrawalForm);
        model.addAttribute("solde", withdrawalPageDto.getSolde());
        model.addAttribute("withdrawalForm", withdrawalPageDto.getWithdrawalForm());

        if (withdrawalForm.getAmount() == null || withdrawalForm.getAmount() == 0) {
            result.rejectValue(
                    "amount",
                    "",
                    "Withdrawal mandatory");
            return "withdrawal";
        }

        if (withdrawalForm.getAmount() < ApplicationConfiguration.MINIMUM_AMOUNT_WITHDRAWAL) {
            result.rejectValue(
                    "amount",
                    "",
                    "Minimum withdrawal amount is " + ApplicationConfiguration.MINIMUM_AMOUNT_WITHDRAWAL + "€");
        }

        if (withdrawalForm.getAmount() > ApplicationConfiguration.MAXIMUM_AMOUNT_WITHDRAWAL) {
            result.rejectValue(
                    "amount",
                    "",
                    "Maximum withdrawal amount is " + ApplicationConfiguration.MAXIMUM_AMOUNT_WITHDRAWAL + "€");
        }

        if (!withdrawalPageService.balanceIsSufficient(principal.getName(), withdrawalForm.getAmount())) {
            result.rejectValue(
                    "amount",
                    "",
                    "You can't withdraw more than your balance");
        }

        if (result.hasErrors()) {
            return "withdrawal";
        }

        Boolean success = withdrawalPageService.withdrawalMoney(
                principal.getName(),
                withdrawalForm.getAmount(),
                null);

        if (success) {
            return "redirect:/profile/withdrawal?success";
        } else {
            return "redirect:/profile/withdrawal?error";
        }
    }
}