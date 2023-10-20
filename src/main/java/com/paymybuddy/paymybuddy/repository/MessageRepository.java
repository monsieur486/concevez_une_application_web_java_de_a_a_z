package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.entity.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {

}
