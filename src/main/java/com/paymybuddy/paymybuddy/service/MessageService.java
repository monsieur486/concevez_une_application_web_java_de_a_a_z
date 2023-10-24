package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.dto.form.MessageFormDto;
import com.paymybuddy.paymybuddy.entity.Message;
import com.paymybuddy.paymybuddy.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void saveMessage(MessageFormDto messageFormDto) {
        Message message = new Message();
        message.setEmail(messageFormDto.getEmail());
        message.setContent(messageFormDto.getContent());
        messageRepository.save(message);
        log.info("User send message : {}", messageFormDto.getEmail());
    }
}
