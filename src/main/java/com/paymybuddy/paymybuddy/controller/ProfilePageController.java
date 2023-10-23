package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.ConnectionDto;
import com.paymybuddy.paymybuddy.dto.ProfilePageDto;
import com.paymybuddy.paymybuddy.service.ProfilePageService;
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
                                  ConnectionDto connectionDto
    ) {
        render(connectionDto, principal, page, size, model);

        return ACTIVE_PAGE;
    }

    @PostMapping("/profile")
    public String addMessage(@ModelAttribute("connectionForm") ConnectionDto connectionForm,
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
                    "Email is required");
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
                    "This user doesn't exist. Friend must be registered");
        }

        if (profilePageService.alreadyExistsByFriend(principal.getName(), connectionForm.getEmail())) {
            result.rejectValue(
                    "email",
                    "",
                    "You are already connected with this user");
        }

        if (connectionForm.getNickname().isEmpty()) {
            result.rejectValue(
                    "nickname",
                    "",
                    "Nickname is required");
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

    private void render(@ModelAttribute("connectionForm") ConnectionDto connectionForm, Principal principal, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, Model model) {
        ProfilePageDto profilePageDto = profilePageService.renderProfilePage(principal.getName(), page, size, connectionForm);
        model.addAttribute("activePage", ACTIVE_PAGE);
        model.addAttribute("userConnect", profilePageDto.getUserConnected());
        model.addAttribute("connectionForm", profilePageDto.getConnectionForm());
        model.addAttribute("connections", profilePageDto.getConnections());
        model.addAttribute("pageNumbers", profilePageDto.getPageNumbers());
        model.addAttribute("currentPage", profilePageDto.getCurrentPage());
        model.addAttribute("totalPage", profilePageDto.getTotalPage());
    }
}
