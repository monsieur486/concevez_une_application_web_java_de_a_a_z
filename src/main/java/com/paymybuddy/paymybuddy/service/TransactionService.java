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

/**
 * Service class for handling transaction related operations.
 */
@Service
@Slf4j
@Transactional
public class TransactionService {

    private final TransactionRepository transactionRepository;

    /**
     * Constructor for TransactionService.
     *
     * @param transactionRepository  the transaction repository
     */
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * Adds a new transaction to the repository.
     *
     * @param connection   the connection object
     * @param amount       the amount of the transaction
     * @param description  the description of the transaction
     */
    public void addTransaction(Connection connection, Integer amount, String description) {
        Transaction transaction = new Transaction();
        transaction.setConnection(connection);
        transaction.setAmount(amount);
        transaction.setDescription(description);
        log.info("Transaction added : {}", transaction);
        transactionRepository.save(transaction);
    }

    /**
     * Retrieves a page of transactions for a user.
     *
     * @param user  the user object
     * @param page  the page number
     * @param size  the page size
     * @return      a page of transactions
     */
    public Page<Transaction> getTransactions(User user, int page, int size) {
        return transactionRepository.findAllByConnection_UserOrderByIdDesc(user, PageRequest.of(page, size));
    }

    /**
     * Deletes transactions by connection id.
     *
     * @param id  the id of the connection
     */
    public void deleteTransactionsByConnectionId(Long id) {
        transactionRepository.deleteTransactionsByConnection_Id(id);
    }
}
