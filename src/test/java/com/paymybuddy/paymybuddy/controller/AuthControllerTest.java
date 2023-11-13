package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.form.UserFormDto;
import com.paymybuddy.paymybuddy.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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

    @MockBean
    private UserService userService;

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
        when(userService.alreadyExist(any(String.class))).thenReturn(false);

        this.mockMvc
                .perform(post("/register/save")
                        .param("email", "demo@test.fr")
                        .param("password", "Password1!")
                        .param("passwordForVerification", "Password1!")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
        ;
    }

    @Test
    void registrationWithInvalidEmail() throws Exception  {
        when(userService.alreadyExist(any(String.class))).thenReturn(false);

        this.mockMvc
                .perform(post("/register/save")
                        .param("email", "demo")
                        .param("password", "Password1!")
                        .param("passwordForVerification", "Password1!")
                        .with(csrf())
                )
                .andExpect(model()
                        .attributeHasFieldErrors("user", "email")
                )
                .andExpect(status().isOk())
        ;
    }

    @Test
    void registrationWithExistingEmail() throws Exception  {
        when(userService.alreadyExist(any(String.class))).thenReturn(true);

        this.mockMvc
                .perform(post("/register/save")
                        .param("email", "demo@test.fr")
                        .param("password", "Password1!")
                        .param("passwordForVerification", "Password1!")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
        ;
    }
}