package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.entity.Transaction;
import com.paymybuddy.paymybuddy.entity.User;
import com.paymybuddy.paymybuddy.service.TransactionService;
import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
public class TransferPageController {

    private final UserService userService;

    private final TransactionService transactionService;

    public TransferPageController(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @RequestMapping(value = "/transfer")
    public String showTransferPage(Model model,
                                   Principal principal,
                                   @RequestParam("page") Optional<Integer> page,
                                   @RequestParam("size") Optional<Integer> size
    ) {
        int currentPage = page.orElse(1);
        if (currentPage < 1) currentPage = 1;
        int pageSize = size.orElse(5);

        String activePage = "transfer";
        User user = userService.findByEmail(principal.getName());
        Page<Transaction> transactions = transactionService.getTransactions(user, currentPage - 1, pageSize);
        model.addAttribute("activePage", activePage);
        model.addAttribute("user", user);
        model.addAttribute("transactions", transactions);
        int totalPages = transactions.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPage", totalPages);
        }
        return "transfer";
    }
}
