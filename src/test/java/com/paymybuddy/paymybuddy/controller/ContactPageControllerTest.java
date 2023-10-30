package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.form.ContactFormDto;
import com.paymybuddy.paymybuddy.entity.Message;
import com.paymybuddy.paymybuddy.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class ContactPageControllerTest {

    protected MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

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
    void showContactPage() throws Exception {
        Message message = new Message();
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/contact"))
                .andExpect(status().isOk())
                .andExpect(view().name("contact"))
                .andExpect(model().attributeExists("activePage"))
                .andExpect(model().attribute("activePage", "contact"))
                .andExpect(model().attributeExists("contactForm"))
        ;
    }

    @Test
    void addMessage() throws Exception {
//        ContactFormDto message = new ContactFormDto();
//        message.setEmail("demo@test.fr");
//        message.setContent("This is a test message");
//
//
//
//        this.mockMvc
//                .perform(MockMvcRequestBuilders.post("/contact")
//                        .content(message.toString())
//                        .with(csrf()))
//                .andExpect(status().isOk())
//        ;
    }
}