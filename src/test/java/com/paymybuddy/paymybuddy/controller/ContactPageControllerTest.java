package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.form.ContactFormDto;
import com.paymybuddy.paymybuddy.entity.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class ContactPageControllerTest {

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
    void showContactPage() throws Exception {
        this.mockMvc
                .perform(get("/contact"))
                .andExpect(status().isOk())
                .andExpect(view().name("contact"))
                .andExpect(model().attributeExists("activePage"))
                .andExpect(model().attribute("activePage", "contact"))
                .andExpect(model().attributeExists("contactForm"))
        ;
    }

    // Test add message with valid data
    @Test
    void addMessageWithValidData() throws Exception {
        ContactFormDto contactFormDto = new ContactFormDto();
        contactFormDto.setEmail("demo@test.fr");
        contactFormDto.setContent("Test message");

        this.mockMvc
                .perform(post("/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contactFormDto.toString())
                        .with(csrf()))
                .andExpect(status().isOk())
        ;
    }

    // Test add message with invalid email
    @Test
    void addMessageWithInvalidEmail() throws Exception {
        ContactFormDto contactFormDto = new ContactFormDto();
        contactFormDto.setEmail("demo");
        contactFormDto.setContent("Test message");

        this.mockMvc
                .perform(post("/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contactFormDto.toJson())
                        .with(csrf()))
                .andExpect(status().isOk())
        ;
    }

    // Test add message with invalid content
    @Test
    void addMessageWithInvalidContent() throws Exception {
        ContactFormDto contactFormDto = new ContactFormDto();
        contactFormDto.setEmail("demo@test.fr");

        this.mockMvc
                .perform(post("/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contactFormDto.toJson())
                        .with(csrf()))
                .andExpect(status().isOk())
        ;
    }

}

