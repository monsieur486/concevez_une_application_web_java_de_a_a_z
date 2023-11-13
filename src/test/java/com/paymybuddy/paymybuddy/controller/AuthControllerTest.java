package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.form.UserFormDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
class AuthControllerTest {

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void showRegistrationForm() throws Exception {
        this.mockMvc
                .perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("activePage"))
                .andExpect(model().attribute("activePage", "register"))
                .andExpect(model().attributeExists("user"))
        ;
    }

    @Test
    void registration() throws Exception  {
        UserFormDto user = new UserFormDto();
        user.setEmail("demo@test.fr");
        user.setPassword("demo");
        user.setPasswordForVerification("demo");

        this.mockMvc
                .perform(post("/register/save")
                        .characterEncoding("UTF-8")
                        .contentType("application/json")
                        .content(user.toJson())
                        .with(csrf())
                )
                .andExpect(status().isOk())
        ;
    }
}