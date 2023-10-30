package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.dto.form.ContactFormDto;
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

    public void saveMessage(ContactFormDto contactFormDto) {
        Message message = new Message();
        message.setEmail(contactFormDto.getEmail());
        message.setContent(contactFormDto.getContent());
        messageRepository.save(message);
        log.info("User send contactForm : {}", contactFormDto.getEmail());
    }
}
