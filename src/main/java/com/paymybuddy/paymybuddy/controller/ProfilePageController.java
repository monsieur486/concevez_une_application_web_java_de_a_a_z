package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.form.ConnectionFormDto;
import com.paymybuddy.paymybuddy.dto.page.ProfilePageDto;
import com.paymybuddy.paymybuddy.service.page.ProfilePageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

/**
 * Controller for handling profile page related requests.
 */
@Controller
@Slf4j
public class ProfilePageController {

    private static final String ACTIVE_PAGE = "profile";

    private final ProfilePageService profilePageService;

    /**
     * Constructor for ProfilePageController.
     *
     * @param profilePageService the service to handle profile page related operations
     */
    public ProfilePageController(ProfilePageService profilePageService) {
        this.profilePageService = profilePageService;
    }

    /**
     * Handles GET requests to show the profile page.
     *
     * @param model             the model to add attributes to for rendering in the view
     * @param principal         the currently authenticated user
     * @param page              the page number for pagination
     * @param size              the size of the page for pagination
     * @param connectionFormDto the form data for adding a new connection
     * @return the name of the view to render
     */
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

    /**
     * Handles POST requests to add a new connection.
     *
     * @param connectionForm the form data for the new connection
     * @param principal      the currently authenticated user
     * @param result         the binding result to hold validation errors
     * @param page           the page number for pagination
     * @param size           the size of the page for pagination
     * @param model          the model to add attributes to for rendering in the view
     * @return the name of the view to render
     */
    @PostMapping("/profile")
    public String addConnection(@ModelAttribute("connectionForm") ConnectionFormDto connectionForm,
                                Principal principal,
                                BindingResult result,
                                @RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size,
                                Model model) {

        render(connectionForm, principal, page, size, model);

        // Validation for the connection form
        if (result.hasErrors()) {
            return ACTIVE_PAGE;
        }

        profilePageService.addConnection(principal.getName(), connectionForm);

        return "redirect:/" + ACTIVE_PAGE + "?success";
    }

    /**
     * Handles GET requests to delete a connection.
     *
     * @param id the id of the connection to delete
     * @return the name of the view to render
     */
    @GetMapping(value = "/profile/delete/{id}")
    public String deleteConnection(@PathVariable("id") Long id) {
        profilePageService.deleteConnection(id);
        return "redirect:/" + ACTIVE_PAGE;
    }

    /**
     * Renders the profile page by adding necessary attributes to the model.
     *
     * @param connectionForm the form data for the new connection
     * @param principal      the currently authenticated user
     * @param page           the page number for pagination
     * @param size           the size of the page for pagination
     * @param model          the model to add attributes to for rendering in the view
     */
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
