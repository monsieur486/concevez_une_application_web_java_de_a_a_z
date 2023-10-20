package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.dto.MessageDto;
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

    public void saveMessage(MessageDto messageDto) {
        Message message = new Message();
        message.setEmail(messageDto.getEmail());
        message.setContent(messageDto.getContent());
        messageRepository.save(message);
        log.info("User send message : {}", messageDto.getEmail());
    }
}
