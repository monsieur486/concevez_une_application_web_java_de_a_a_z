package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.dto.form.MessageFormDto;
import com.paymybuddy.paymybuddy.entity.Message;
import com.paymybuddy.paymybuddy.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @InjectMocks
    MessageService service;

    @Mock
    MessageRepository dao;


    @Test
    void saveMessage() {
        MessageFormDto messageFormDto = new MessageFormDto();
        messageFormDto.setEmail("test@test.fr");
        messageFormDto.setContent("test");
        service.saveMessage(messageFormDto);
        verify(dao, times(1)).save(any(Message.class));
    }
}