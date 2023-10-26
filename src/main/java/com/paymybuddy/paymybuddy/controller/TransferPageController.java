package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.config.ApplicationConfiguration;
import com.paymybuddy.paymybuddy.dto.form.TransactionFormDto;
import com.paymybuddy.paymybuddy.dto.page.TransferPageDto;
import com.paymybuddy.paymybuddy.service.page.TransferPageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;

@Controller
@Slf4j
public class TransferPageController {

    private static final String ACTIVE_PAGE = "transfer";

    private final TransferPageService transferPageService;

    public TransferPageController(TransferPageService transferPageService) {
        this.transferPageService = transferPageService;
    }

    @GetMapping(value = "/transfer")
    public String showTransferPage(Model model,
                                   Principal principal,
                                   TransactionFormDto transactionForm,
                                   @RequestParam("page") Optional<Integer> page,
                                   @RequestParam("size") Optional<Integer> size
    ) {

        render(model, principal, transactionForm, page, size);

        return ACTIVE_PAGE;
    }

    @PostMapping(value = "/transfer")
    public String addTransaction(@ModelAttribute("transactionForm") TransactionFormDto transactionForm,
                                 BindingResult result,
                                 Model model,
                                 Principal principal,
                                 @RequestParam("page") Optional<Integer> page,
                                 @RequestParam("size") Optional<Integer> size
    ) {

        render(model, principal, transactionForm, page, size);

        if(transactionForm.getAmount() == null) {
            result.rejectValue(
                    "amount",
                    "",
                    "Amount mandatory");
            return ACTIVE_PAGE;
        }

        if (transactionForm.getAmount() < ApplicationConfiguration.MINIMUM_AMOUNT_TRANSACTION) {
            result.rejectValue(
                    "amount",
                    "",
                    "Minimum transaction "
                            + ApplicationConfiguration.MINIMUM_AMOUNT_TRANSACTION
                            + ApplicationConfiguration.CURRENCY);
        }

        if(transactionForm.getAmount() > ApplicationConfiguration.MAXIMUM_AMOUNT_TRANSACTION) {
            result.rejectValue(
                    "amount",
                    "",
                    "Maximum transaction "
                            + ApplicationConfiguration.MAXIMUM_AMOUNT_TRANSACTION
                            + ApplicationConfiguration.CURRENCY);
        }

        if(transactionForm.getDescription() == null || transactionForm.getDescription().isEmpty()){
            result.rejectValue(
                    "description",
                    "",
                    "Description mandatory");

        }

        if (transactionForm.getConnectionId() == 0L) {
            result.rejectValue(
                    "connectionId",
                    "",
                    "Please select a connection");
        }

        if(!transferPageService.balanceIsSufficient(principal.getName(), transactionForm.getAmount())) {
            result.rejectValue(
                    "amount",
                    "",
                    "Insufficient balance");
        }

        if (result.hasErrors()) {
            return ACTIVE_PAGE;
        }

        transferPageService.addTransaction(transactionForm, principal.getName());

        return "redirect:/" + ACTIVE_PAGE + "?success";
    }
    private void render(Model model, Principal principal, TransactionFormDto transactionForm, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        String activePage = "transfer";
        model.addAttribute("activePage", activePage);
        TransferPageDto transferPageDto = transferPageService.createTransferPageDto(transactionForm, principal.getName(), page, size);

        model.addAttribute("solde", transferPageDto.getSolde());
        model.addAttribute("transactionForm", transferPageDto.getTransactionForm());
        model.addAttribute("transactions", transferPageDto.getTransactions());
        model.addAttribute("pageNumbers", transferPageDto.getPageNumbers());
        model.addAttribute("currentPage", transferPageDto.getCurrentPage());
        model.addAttribute("totalPage", transferPageDto.getTotalPage());
    }
}
