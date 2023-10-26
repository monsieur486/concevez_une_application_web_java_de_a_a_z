package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.dto.form.MessageFormDto;
import com.paymybuddy.paymybuddy.entity.Message;
import com.paymybuddy.paymybuddy.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @InjectMocks
    MessageService messageService;

    @Mock
    MessageRepository messageRepository;


    @Test
    void saveMessage() {
        MessageFormDto messageFormDto = new MessageFormDto();
        messageFormDto.setEmail("test@test.fr");
        messageFormDto.setContent("test");
        messageService.saveMessage(messageFormDto);
        verify(messageRepository, times(1)).save(any(Message.class));
    }
}