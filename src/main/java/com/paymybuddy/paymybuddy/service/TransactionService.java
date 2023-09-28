package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.entity.Connection;
import com.paymybuddy.paymybuddy.entity.Transaction;
import com.paymybuddy.paymybuddy.entity.User;
import com.paymybuddy.paymybuddy.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Transactional
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

    public Page<Transaction> getTransactions(User user, int page, int size) {
        return transactionRepository.findAllByConnection_UserOrderByIdDesc(user, PageRequest.of(page, size));
    }

    public void deleteTransactionsByConnectionId(Long id) {
        transactionRepository.deleteTransactionsByConnection_Id(id);
    }
}
