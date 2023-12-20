package com.paymybuddy.paymybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * This is a controller class for handling requests related to the home page.
 */
@Controller
public class HomePageController {

    /**
     * This method is mapped to the root ("/") URL and is responsible for showing the home page.
     * It adds an attribute "activePage" to the model with a value of "home".
     *
     * @param model the Model object to which attributes for the view can be added
     * @return a string that represents the name of the view (in this case, "index")
     */
    @GetMapping("/")
    public String showHomePage(Model model) {
        String activePage = "home";
        model.addAttribute("activePage", activePage);
        return "index";
    }
}
