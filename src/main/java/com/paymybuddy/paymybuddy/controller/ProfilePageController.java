package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.ConnectionPageDto;
import com.paymybuddy.paymybuddy.entity.User;
import com.paymybuddy.paymybuddy.service.ConnectionPageDtoService;
import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;

@Controller
public class ProfilePageController {

    private final UserService userService;
    private final ConnectionPageDtoService connectionPageDtoService;

    public ProfilePageController(UserService userService, ConnectionPageDtoService connectionPageDtoService) {
        this.userService = userService;
        this.connectionPageDtoService = connectionPageDtoService;
    }

    @RequestMapping(value = "/profile")
    public String showAdminPage(Model model,
                                Principal principal,
                                @RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size
    ) {
        User user = userService.findByEmail(principal.getName());
        String activePage = "profile";
        model.addAttribute("activePage", activePage);
        model.addAttribute("user", user);

        ConnectionPageDto connectionPageDto = connectionPageDtoService.createConnectionPageDto(principal.getName(), page, size);
        model.addAttribute("userConnect", connectionPageDto.getUserConnect());
        model.addAttribute("connections", connectionPageDto.getConnections());
        model.addAttribute("pageNumbers", connectionPageDto.getPageNumbers());
        model.addAttribute("currentPage", connectionPageDto.getCurrentPage());
        model.addAttribute("totalPage", connectionPageDto.getTotalPage());

        return "profile";
    }

    @GetMapping(value = "/profile/delete/{id}")
    public String deleteConnection(@PathVariable("id") Long id) {
        connectionPageDtoService.deleteConnection(id);
        return "redirect:/profile";
    }
}
