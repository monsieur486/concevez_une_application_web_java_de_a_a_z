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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
class TransferPageControllerTest {
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
    void showTransferPage() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("demo@test.fr");

        User userTest = new User(1L, "demo@test.fr", "password", 0);
        when(userService.findByEmail(any(String.class))).thenReturn(userTest);

        this.mockMvc
                .perform(get("/transfer").principal(mockPrincipal))
                .andExpect(status().isOk())
                .andExpect(view().name("transfer"))
                .andExpect(model().attributeExists("activePage"))
                .andExpect(model().attribute("activePage", "transfer"))
                ;
    }

    @Test
    @WithMockUser("demo@test.fr")
    void addTransactionWithCorrectData() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("demo@test.fr");

        User userTest = new User(1L, "demo@test.fr", "password", 10000);
        when(userService.findByEmail(any(String.class))).thenReturn(userTest);

        this.mockMvc
                .perform(post("/transfer")
                        .principal(mockPrincipal)
                        .param("connectionId", "2")
                        .param("amount", "10")
                        .param("description", "description")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/transfer?success"))
        ;
    }

    @Test
    @WithMockUser("demo@test.fr")
    void addTransactionWithIncorrectConnectionId() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("demo@test.fr");

        User userTest = new User(1L, "demo@test.fr", "password", 10000);
        when(userService.findByEmail(any(String.class))).thenReturn(userTest);

        this.mockMvc
                .perform(post("/transfer")
                        .principal(mockPrincipal)
                        .param("connectionId", "0")
                        .param("amount", "10")
                        .param("description", "description")
                        .with(csrf())
                )
                .andExpect(model()
                        .attributeHasFieldErrors("transactionForm", "connectionId")
                )
                .andExpect(status().isOk())
        ;
    }

    @Test
    @WithMockUser("demo@test.fr")
    void addTransactionWithEmptyAmount() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("demo@test.fr");

        User userTest = new User(1L, "demo@test.fr", "password", 10000);
        when(userService.findByEmail(any(String.class))).thenReturn(userTest);

        this.mockMvc
                .perform(post("/transfer")
                        .principal(mockPrincipal)
                        .param("connectionId", "2")
                        .param("amount", "")
                        .param("description", "description")
                        .with(csrf())
                )
                .andExpect(model()
                        .attributeHasFieldErrors("transactionForm", "amount")
                )
                .andExpect(status().isOk())
        ;
    }

    @Test
    @WithMockUser("demo@test.fr")
    void addTransactionWithMinimumAmount() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("demo@test.fr");

        User userTest = new User(1L, "demo@test.fr", "password", 10000);
        when(userService.findByEmail(any(String.class))).thenReturn(userTest);

        int minimumAmount = ApplicationConfiguration.MINIMUM_AMOUNT_TRANSACTION -1;

        this.mockMvc
                .perform(post("/transfer")
                        .principal(mockPrincipal)
                        .param("connectionId", Integer.toString(minimumAmount))
                        .param("amount", "")
                        .param("description", "description")
                        .with(csrf())
                )
                .andExpect(model()
                        .attributeHasFieldErrors("transactionForm", "amount")
                )
                .andExpect(status().isOk())
        ;
    }

    @Test
    @WithMockUser("demo@test.fr")
    void addTransactionWithMaximumAmount() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("demo@test.fr");

        User userTest = new User(1L, "demo@test.fr", "password", 10000);
        when(userService.findByEmail(any(String.class))).thenReturn(userTest);

        int maximumAmount = ApplicationConfiguration.MAXIMUM_AMOUNT_TRANSACTION +1;

        this.mockMvc
                .perform(post("/transfer")
                        .principal(mockPrincipal)
                        .param("connectionId", Integer.toString(maximumAmount))
                        .param("amount", "")
                        .param("description", "description")
                        .with(csrf())
                )
                .andExpect(model()
                        .attributeHasFieldErrors("transactionForm", "amount")
                )
                .andExpect(status().isOk())
        ;
    }

    @Test
    @WithMockUser("demo@test.fr")
    void addTransactionWithEmptyDescription() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("demo@test.fr");

        User userTest = new User(1L, "demo@test.fr", "password", 10000);
        when(userService.findByEmail(any(String.class))).thenReturn(userTest);

        this.mockMvc
                .perform(post("/transfer")
                        .principal(mockPrincipal)
                        .param("connectionId", "2")
                        .param("amount", "10")
                        .param("description", "")
                        .with(csrf())
                )
                .andExpect(model()
                        .attributeHasFieldErrors("transactionForm", "description")
                )
                .andExpect(status().isOk())
        ;

    }

    @Test
    @WithMockUser("demo@test.fr")
    void addTransactionWithInsufficientBalance() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("demo@test.fr");

        User userTest = new User(1L, "demo@test.fr", "password", 100);
        when(userService.findByEmail(any(String.class))).thenReturn(userTest);

        this.mockMvc
                .perform(post("/transfer")
                        .principal(mockPrincipal)
                        .param("connectionId", "2")
                        .param("amount", "1000")
                        .param("description", "description")
                        .with(csrf())
                )
                .andExpect(model()
                        .attributeHasFieldErrors("transactionForm", "amount")
                )
                .andExpect(status().isOk())
        ;
    }
}