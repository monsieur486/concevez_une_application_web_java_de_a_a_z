package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.config.ApplicationConfiguration;
import com.paymybuddy.paymybuddy.entity.User;
import com.paymybuddy.paymybuddy.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class WithdrawalPageControllerTest {
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
    void showWithdrawalPage() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("demo@test.fr");

        User userTest = new User(1L, "demo@test.fr", "password", 1000);
        when(userService.findByEmail(any(String.class))).thenReturn(userTest);

        this.mockMvc
                .perform(get("/profile/withdrawal").principal(mockPrincipal))
                .andExpect(status().isOk())
                .andExpect(view().name("withdrawal"))
                .andExpect(model().attributeExists("activePage"))
                .andExpect(model().attribute("activePage", "profile"))
                .andExpect(model().attributeExists("solde"))
                .andExpect(model().attributeExists("withdrawalForm"))
        ;
    }

    @Test
    @WithMockUser("demo@test.fr")
    void withdrawalAmountWithCorrectValue() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("demo@test.fr");

        User userTest = new User(1L, "demo@test.fr", "password", 100000);
        when(userService.findByEmail(any(String.class))).thenReturn(userTest);

        this.mockMvc
                .perform(post("/profile/withdrawal")
                        .principal(mockPrincipal)
                        .param("amount", "100")
                        .with(csrf())
                )
                .andExpect(model().hasNoErrors())
        ;
    }

    @Test
    @WithMockUser("demo@test.fr")
    void withdrawalAmountWithIncorrectValueEmptyAmount() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("demo@test.fr");

        User userTest = new User(1L, "demo@test.fr", "password", 100000);
        when(userService.findByEmail(any(String.class))).thenReturn(userTest);

        this.mockMvc
                .perform(post("/profile/withdrawal")
                        .principal(mockPrincipal)
                        .param("amount", "0")
                        .with(csrf())
                )
                .andExpect(model()
                        .attributeHasFieldErrors("withdrawalForm", "amount")
                )
                .andExpect(status().isOk())
        ;
    }

    @Test
    @WithMockUser("demo@test.fr")
    void withdrawalAmountWithIncorrectValueMiniumAmount() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("demo@test.fr");

        User userTest = new User(1L, "demo@test.fr", "password", 100000);
        when(userService.findByEmail(any(String.class))).thenReturn(userTest);

        int miniumAmount = ApplicationConfiguration.MINIMUM_AMOUNT_WITHDRAWAL - 1;

        this.mockMvc
                .perform(post("/profile/withdrawal")
                        .principal(mockPrincipal)
                        .param("amount", Integer.toString(miniumAmount))
                        .with(csrf())
                )
                .andExpect(model()
                        .attributeHasFieldErrors("withdrawalForm", "amount")
                )
                .andExpect(status().isOk())
        ;
    }

    @Test
    @WithMockUser("demo@test.fr")
    void withdrawalAmountWithIncorrectValueMaximumAmount() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("demo@test.fr");

        User userTest = new User(1L, "demo@test.fr", "password", 100000);
        when(userService.findByEmail(any(String.class))).thenReturn(userTest);

        int mamimumAmount = ApplicationConfiguration.MAXIMUM_AMOUNT_WITHDRAWAL + 1;

        this.mockMvc
                .perform(post("/profile/withdrawal")
                        .principal(mockPrincipal)
                        .param("amount", Integer.toString(mamimumAmount))
                        .with(csrf())
                )
                .andExpect(model()
                        .attributeHasFieldErrors("withdrawalForm", "amount")
                )
                .andExpect(status().isOk())
        ;
    }

    @Test
    @WithMockUser("demo@test.fr")
    void withdrawalAmountWithIncorrectValueNoBalance() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("demo@test.fr");

        User userTest = new User(1L, "demo@test.fr", "password", 0);
        when(userService.findByEmail(any(String.class))).thenReturn(userTest);

        this.mockMvc
                .perform(post("/profile/withdrawal")
                        .principal(mockPrincipal)
                        .param("amount", Integer.toString(1))
                        .with(csrf())
                )
                .andExpect(model()
                        .attributeHasFieldErrors("withdrawalForm", "amount")
                )
                .andExpect(status().isOk())
        ;
    }

}