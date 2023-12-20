package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.entity.Message;
import org.springframework.data.repository.CrudRepository;

/**
 * MessageRepository is an interface that extends CrudRepository.
 * It provides methods to perform CRUD operations on Message entities.
 */
public interface MessageRepository extends CrudRepository<Message, Long> {

}
