package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.bank.DepositInformation;
import com.paymybuddy.paymybuddy.dto.form.ContactFormDto;
import com.paymybuddy.paymybuddy.dto.form.DepositFormDto;
import com.paymybuddy.paymybuddy.dto.page.DepositPageDto;
import com.paymybuddy.paymybuddy.entity.User;
import com.paymybuddy.paymybuddy.service.UserService;
import com.paymybuddy.paymybuddy.service.page.DepositPageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

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
class DepositPageControllerTest {
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
    @WithMockUser("demo@test.fr")
    void showDepositPage() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("demo@test.fr");

        User userTest = new User(1L,"demo@test.fr","password",1000);
        when(userService.findByEmail(any(String.class))).thenReturn(userTest);

        this.mockMvc
                .perform(get("/profile/deposit").principal(mockPrincipal))
                .andExpect(status().isOk())
                .andExpect(view().name("deposit"))
                .andExpect(model().attributeExists("activePage"))
                .andExpect(model().attribute("activePage", "profile"))
                .andExpect(model().attributeExists("solde"))
                .andExpect(model().attributeExists("depositForm"))
        ;
    }

    @Test
    @WithMockUser("demo@test.fr")
    void depositAmountWithCorrectValue() throws Exception  {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("demo@test.fr");

        User userTest = new User(1L,"demo@test.fr","password",0);
        when(userService.findByEmail(any(String.class))).thenReturn(userTest);

        this.mockMvc
                .perform(post("/profile/deposit")
                        .principal(mockPrincipal)
                        .param("amount", "100")
                        .with(csrf())
                )
                .andExpect(model().hasNoErrors())
        ;
    }
}