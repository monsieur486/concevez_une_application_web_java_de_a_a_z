package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.form.ConnectionFormDto;
import com.paymybuddy.paymybuddy.dto.page.ProfilePageDto;
import com.paymybuddy.paymybuddy.service.page.ProfilePageService;
import com.paymybuddy.paymybuddy.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@Slf4j
public class ProfilePageController {

    private static final String ACTIVE_PAGE = "profile";

    private final ProfilePageService profilePageService;

    public ProfilePageController(ProfilePageService profilePageService) {
        this.profilePageService = profilePageService;
    }

    @GetMapping(value = "/profile")
    public String showProfilePage(Model model,
                                  Principal principal,
                                  @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size,
                                  ConnectionFormDto connectionFormDto
    ) {
        render(connectionFormDto, principal, page, size, model);

        return ACTIVE_PAGE;
    }

    @PostMapping("/profile")
    public String addMessage(@ModelAttribute("connectionForm") ConnectionFormDto connectionForm,
                             Principal principal,
                             BindingResult result,
                             @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size,
                             Model model) {

        render(connectionForm, principal, page, size, model);

        if (connectionForm.getEmail().isEmpty()) {
            result.rejectValue(
                    "email",
                    "",
                    "Email mandatory");
        }

        if (!StringUtil.isValidEmail(connectionForm.getEmail())) {
            result.rejectValue(
                    "email",
                    "",
                    "Please enter a valid email address");
        }

        if (connectionForm.getEmail().equals(principal.getName())) {
            result.rejectValue(
                    "email",
                    "",
                    "You can't add yourself");
        }

        if (!profilePageService.friendAlreadyExists(connectionForm.getEmail())) {
            result.rejectValue(
                    "email",
                    "",
                    "User not found. Friend must be registered.");
        }

        if (profilePageService.alreadyExistsByFriend(principal.getName(), connectionForm.getEmail())) {
            result.rejectValue(
                    "email",
                    "",
                    "Connection already in list");
        }

        if (connectionForm.getNickname().isEmpty()) {
            result.rejectValue(
                    "nickname",
                    "",
                    "Nickname mandatory");
        }

        if (result.hasErrors()) {
            return ACTIVE_PAGE;
        }

        profilePageService.addConnection(principal.getName(), connectionForm);

        return "redirect:/" + ACTIVE_PAGE + "?success";
    }

    @GetMapping(value = "/profile/delete/{id}")
    public String deleteConnection(@PathVariable("id") Long id) {
        profilePageService.deleteConnection(id);
        return "redirect:/" + ACTIVE_PAGE;
    }

    private void render(@ModelAttribute("connectionForm") ConnectionFormDto connectionForm, Principal principal, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, Model model) {
        ProfilePageDto profilePageDto = profilePageService.renderProfilePage(principal.getName(), page, size, connectionForm);
        model.addAttribute("activePage", ACTIVE_PAGE);
        model.addAttribute("userConnect", profilePageDto.getUserConnected());
        model.addAttribute("solde", profilePageDto.getSolde());
        model.addAttribute("connectionForm", profilePageDto.getConnectionForm());
        model.addAttribute("connections", profilePageDto.getConnections());
        model.addAttribute("pageNumbers", profilePageDto.getPageNumbers());
        model.addAttribute("currentPage", profilePageDto.getCurrentPage());
        model.addAttribute("totalPage", profilePageDto.getTotalPage());
    }
}
