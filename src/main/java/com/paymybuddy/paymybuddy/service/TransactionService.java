package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.entity.Connection;
import com.paymybuddy.paymybuddy.entity.Transaction;
import com.paymybuddy.paymybuddy.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void addTransaction(Connection connection, Integer amount, String description) {
        Transaction transaction = new Transaction();
        transaction.setConnection(connection);
        transaction.setAmount(amount);
        transaction.setDescription(description);
        log.info("Transaction added : {}", transaction);
        transactionRepository.save(transaction);
    }
}
