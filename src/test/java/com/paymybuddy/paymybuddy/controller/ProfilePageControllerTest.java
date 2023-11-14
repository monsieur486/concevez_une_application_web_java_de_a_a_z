package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.entity.User;
import com.paymybuddy.paymybuddy.service.ConnectionService;
import com.paymybuddy.paymybuddy.service.TransactionService;
import com.paymybuddy.paymybuddy.service.UserService;
import com.paymybuddy.paymybuddy.service.page.ProfilePageService;
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
class ProfilePageControllerTest {
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
    void showProfilePage() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("demo@test.fr");

        User userTest = new User(1L, "demo@test.fr", "password", 0);
        when(userService.findByEmail(any(String.class))).thenReturn(userTest);

        this.mockMvc
                .perform(get("/profile").principal(mockPrincipal))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"))
                .andExpect(model().attributeExists("activePage"))
                .andExpect(model().attribute("activePage", "profile"))
        ;
    }

    @Test
    @WithMockUser("demo@test.fr")
    void addConnectionWithIncorrectValueEmail() throws Exception {

        User userTest = new User(1L, "demo@test.fr", "password", 0);
        when(userService.findByEmail("demo@test.fr")).thenReturn(userTest);
        when(userService.alreadyExist("user99@test.fr")).thenReturn(true);

        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn(userTest.getEmail());

        ConnectionService connectionService = Mockito.mock(ConnectionService.class);
        when(connectionService.existConnectionByFriend(any(User.class), any(User.class))).thenReturn(false);

        this.mockMvc
                .perform(post("/profile")
                        .principal(mockPrincipal)
                        .param("email", "user99@")
                        .param("nickname", "user01")
                        .with(csrf())
                )
                .andExpect(model()
                        .attributeHasFieldErrors("connectionForm", "email")
                )
                .andExpect(status().isOk())
        ;
    }

    @Test
    @WithMockUser("demo@test.fr")
    void addConnectionWithIncorrectValueEmailEmpty() throws Exception {

        User userTest = new User(1L, "demo@test.fr", "password", 0);
        when(userService.findByEmail("demo@test.fr")).thenReturn(userTest);
        when(userService.alreadyExist("user99@test.fr")).thenReturn(true);

        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn(userTest.getEmail());

        ConnectionService connectionService = Mockito.mock(ConnectionService.class);
        when(connectionService.existConnectionByFriend(any(User.class), any(User.class))).thenReturn(false);

        this.mockMvc
                .perform(post("/profile")
                        .principal(mockPrincipal)
                        .param("email", "")
                        .param("nickname", "user01")
                        .with(csrf())
                )
                .andExpect(model()
                        .attributeHasFieldErrors("connectionForm", "email")
                )
                .andExpect(status().isOk())
        ;
    }

    @Test
    @WithMockUser("demo@test.fr")
    void addConnectionWithIncorrectValueNicknameEmpty() throws Exception {

        User userTest = new User(1L, "demo@test.fr", "password", 0);
        when(userService.findByEmail("demo@test.fr")).thenReturn(userTest);
        when(userService.alreadyExist("user99@test.fr")).thenReturn(true);

        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn(userTest.getEmail());

        ConnectionService connectionService = Mockito.mock(ConnectionService.class);
        when(connectionService.existConnectionByFriend(any(User.class), any(User.class))).thenReturn(false);

        this.mockMvc
                .perform(post("/profile")
                        .principal(mockPrincipal)
                        .param("email", "test@test.fr")
                        .param("nickname", "")
                        .with(csrf())
                )
                .andExpect(model()
                        .attributeHasFieldErrors("connectionForm", "nickname")
                )
                .andExpect(status().isOk())
        ;
    }

    @Test
    @WithMockUser("demo@test.fr")
    void addConnectionWithIncorrectValueSameEmail() throws Exception {

        User userTest = new User(1L, "demo@test.fr", "password", 0);
        when(userService.findByEmail("demo@test.fr")).thenReturn(userTest);
        when(userService.alreadyExist("demo@test.fr")).thenReturn(true);

        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn(userTest.getEmail());

        ConnectionService connectionService = Mockito.mock(ConnectionService.class);
        when(connectionService.existConnectionByFriend(any(User.class), any(User.class))).thenReturn(false);

        this.mockMvc
                .perform(post("/profile")
                        .principal(mockPrincipal)
                        .param("email", "demo@test.fr")
                        .param("nickname", "me")
                        .with(csrf())
                )
                .andExpect(model()
                        .attributeHasFieldErrors("connectionForm", "email")
                )
                .andExpect(status().isOk())
        ;
    }

    @Test
    void deleteConnection() {
    }
}