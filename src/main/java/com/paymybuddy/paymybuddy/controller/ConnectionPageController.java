package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.ConnectionPageDto;
import com.paymybuddy.paymybuddy.service.ConnectionPageDtoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;

@Controller
public class ConnectionPageController {

    private final ConnectionPageDtoService connectionPageDtoService;

    public ConnectionPageController(ConnectionPageDtoService connectionPageDtoService) {
        this.connectionPageDtoService = connectionPageDtoService;
    }


    @RequestMapping(value = "/connections")
    public String showTransferPage(Model model,
                                   Principal principal,
                                   @RequestParam("page") Optional<Integer> page,
                                   @RequestParam("size") Optional<Integer> size
    ) {

        String activePage = "connections";
        model.addAttribute("activePage", activePage);

        ConnectionPageDto connectionPageDto = connectionPageDtoService.createConnectionPageDto(principal.getName(), page, size);
        model.addAttribute("userConnect", connectionPageDto.getUserConnect());
        model.addAttribute("connections", connectionPageDto.getConnections());
        model.addAttribute("pageNumbers", connectionPageDto.getPageNumbers());
        model.addAttribute("currentPage", connectionPageDto.getCurrentPage());
        model.addAttribute("totalPage", connectionPageDto.getTotalPage());

        return "connections";
    }

    @GetMapping(value = "/connections/delete/{id}")
    public String deleteConnection(@PathVariable("id") Long id) {
        connectionPageDtoService.deleteConnection(id);
        return "redirect:/connections";
    }
}
