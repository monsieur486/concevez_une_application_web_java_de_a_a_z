package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.dto.form.ContactFormDto;
import com.paymybuddy.paymybuddy.entity.Message;
import com.paymybuddy.paymybuddy.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service class for handling message related operations.
 */
@Service
@Slf4j
public class MessageService {

    private final MessageRepository messageRepository;

    /**
     * Constructor for MessageService.
     *
     * @param messageRepository the message repository
     */
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * Saves a new message to the repository.
     *
     * @param contactFormDto the contact form data transfer object
     */
    public void saveMessage(ContactFormDto contactFormDto) {
        Message message = new Message();
        message.setEmail(contactFormDto.getEmail());
        message.setContent(contactFormDto.getContent());
        messageRepository.save(message);
        log.info("User send contactForm : {}", contactFormDto.getEmail());
    }
}
