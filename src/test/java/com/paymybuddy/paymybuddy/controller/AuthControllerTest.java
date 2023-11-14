package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.entity.User;
import com.paymybuddy.paymybuddy.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    void registration() throws Exception {
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
    void registrationWithInvalidEmail() throws Exception {
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
    void registrationWithExistingEmail() throws Exception {
        User user = new User(1L, "demo@test.fr", "Password1!", 0);

        when(userService.findByEmail(any(String.class))).thenReturn(user);

        this.mockMvc
                .perform(post("/register/save")
                        .param("email", "demo@test.fr")
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
    void registrationWithInvalidPasswordWithOutUperCase() throws Exception {
        this.mockMvc
                .perform(post("/register/save")
                        .param("email", "demo@test.fr")
                        .param("password", "password1!")
                        .param("passwordForVerification", "password1!")
                        .with(csrf())
                )
                .andExpect(model()
                        .attributeHasFieldErrors("user", "password")
                )
                .andExpect(status().isOk())
        ;
    }

    @Test
    void registrationWithInvalidPasswordWithOutLowerCase() throws Exception {
        this.mockMvc
                .perform(post("/register/save")
                        .param("email", "demo@test.fr")
                        .param("password", "PASSWORD1!")
                        .param("passwordForVerification", "PASSWORD1!")
                        .with(csrf())
                )
                .andExpect(model()
                        .attributeHasFieldErrors("user", "password")
                )
                .andExpect(status().isOk())
        ;
    }

    @Test
    void registrationWithInvalidPasswordWithOutDigit() throws Exception {
        this.mockMvc
                .perform(post("/register/save")
                        .param("email", "demo@test.fr")
                        .param("password", "Password!")
                        .param("passwordForVerification", "Password!")
                        .with(csrf())
                )
                .andExpect(model()
                        .attributeHasFieldErrors("user", "password")
                )
                .andExpect(status().isOk())
        ;
    }

    @Test
    void registrationWithInvalidPasswordLengt() throws Exception {
        this.mockMvc
                .perform(post("/register/save")
                        .param("email", "demo@test.fr")
                        .param("password", "Psd!1")
                        .param("passwordForVerification", "Psd!1")
                        .with(csrf())
                )
                .andExpect(model()
                        .attributeHasFieldErrors("user", "password")
                )
                .andExpect(status().isOk())
        ;
    }

    @Test
    void registrationWithInvalidPasswordMatching() throws Exception {
        this.mockMvc
                .perform(post("/register/save")
                        .param("email", "demo@test.fr")
                        .param("password", "Password1!")
                        .param("passwordForVerification", "Pasword1!")
                        .with(csrf())
                )
                .andExpect(model()
                        .attributeHasFieldErrors("user", "passwordForVerification")
                )
                .andExpect(status().isOk())
        ;
    }


}