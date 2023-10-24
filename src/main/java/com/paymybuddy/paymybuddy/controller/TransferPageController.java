package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.page.TransferPageDto;
import com.paymybuddy.paymybuddy.service.TransferPageDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;

@Controller
public class TransferPageController {

    @Autowired
    private TransferPageDtoService transferPageDtoService;

    public TransferPageController(TransferPageDtoService transferPageDtoService) {
        this.transferPageDtoService = transferPageDtoService;
    }

    @RequestMapping(value = "/transfer")
    public String showTransferPage(Model model,
                                   Principal principal,
                                   @RequestParam("page") Optional<Integer> page,
                                   @RequestParam("size") Optional<Integer> size
    ) {

        String activePage = "transfer";
        model.addAttribute("activePage", activePage);
        TransferPageDto transferPageDto = transferPageDtoService.createTransferPageDto(principal.getName(), page, size);
        model.addAttribute("userConnected", transferPageDto.getUserConnected());
        model.addAttribute("connections", transferPageDto.getConnections());
        model.addAttribute("transactions", transferPageDto.getTransactions());
        model.addAttribute("pageNumbers", transferPageDto.getPageNumbers());
        model.addAttribute("currentPage", transferPageDto.getCurrentPage());
        model.addAttribute("totalPage", transferPageDto.getTotalPage());


        return "transfer";
    }
}
