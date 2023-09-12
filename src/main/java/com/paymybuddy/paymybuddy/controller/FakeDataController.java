package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.utils.DatabaseUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FakeDataController {

    private final DatabaseUtils databaseUtils;

    public FakeDataController(DatabaseUtils databaseUtils) {
        this.databaseUtils = databaseUtils;
    }

    @GetMapping("/contact/fake-data")
    public String createFakeData(Model model) {
        databaseUtils.createFakeData();
        String activePage = "home";
        model.addAttribute("activePage", activePage);
        return "index";
    }
}
