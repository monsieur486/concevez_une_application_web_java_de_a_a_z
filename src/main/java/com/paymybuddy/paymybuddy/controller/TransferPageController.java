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

/**
 * Controller for handling transfer page related requests.
 */
@Controller
@Slf4j
public class TransferPageController {

    private static final String ACTIVE_PAGE = "transfer";

    private final TransferPageService transferPageService;

    /**
     * Constructor for TransferPageController.
     *
     * @param transferPageService the service to handle transfer page related operations
     */
    public TransferPageController(TransferPageService transferPageService) {
        this.transferPageService = transferPageService;
    }

    /**
     * Handles GET requests to show the transfer page.
     *
     * @param model           the model to hold attributes for the view
     * @param principal       the currently authenticated user
     * @param transactionForm the form for the transaction
     * @param page            the page number for pagination
     * @param size            the size of the page for pagination
     * @return the name of the view to render
     */
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

    /**
     * Handles POST requests to add a transaction.
     *
     * @param transactionForm the form for the transaction
     * @param result          the binding result to hold validation errors
     * @param model           the model to hold attributes for the view
     * @param principal       the currently authenticated user
     * @param page            the page number for pagination
     * @param size            the size of the page for pagination
     * @return the name of the view to render
     */
    @PostMapping(value = "/transfer")
    public String addTransaction(@ModelAttribute("transactionForm") TransactionFormDto transactionForm,
                                 BindingResult result,
                                 Model model,
                                 Principal principal,
                                 @RequestParam("page") Optional<Integer> page,
                                 @RequestParam("size") Optional<Integer> size
    ) {

        render(model, principal, transactionForm, page, size);

        if (transactionForm.getConnectionId()==0) {
            result.rejectValue(
                    "connectionId",
                    "",
                    "The connection must be selected");
        }

        if (transactionForm.getAmount() <= 0) {
            result.rejectValue(
                    "amount",
                    "",
                    "The amount must be greater than 0");
        }

        if (transactionForm.getDescription() == null || transactionForm.getDescription().isEmpty()) {
            result.rejectValue(
                    "description",
                    "",
                    "The description cannot be empty");
        }

        if(!transferPageService.balanceIsSufficient(principal.getName(), transactionForm.getAmount())){
            int maxSolde = (int) Math.floor(transferPageService.maximumAmount(principal.getName())/100.0);
            result.rejectValue(
                    "amount",
                    "",
                    "The maximum amount without tax must be less than "
                            + maxSolde
                            + " "
                            + ApplicationConfiguration.CURRENCY
            );
        }


        if (result.hasErrors()) {
            return ACTIVE_PAGE;
        }

        transferPageService.addTransaction(transactionForm, principal.getName());

        return "redirect:/" + ACTIVE_PAGE + "?success";
    }

    /**
     * Helper method to set up the model for the view.
     *
     * @param model           the model to hold attributes for the view
     * @param principal       the currently authenticated user
     * @param transactionForm the form for the transaction
     * @param page            the page number for pagination
     * @param size            the size of the page for pagination
     */
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

