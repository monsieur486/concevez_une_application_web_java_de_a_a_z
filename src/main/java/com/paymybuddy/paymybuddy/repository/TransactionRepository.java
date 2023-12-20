package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.entity.Transaction;
import com.paymybuddy.paymybuddy.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Transaction entity.
 * This interface extends CrudRepository to provide CRUD functionality for the Transaction entity.
 */
@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    /**
     * Find a transaction by its id.
     * @param id The id of the transaction.
     * @return The transaction with the given id.
     */
    Transaction findById(long id);

    /**
     * Find all transactions associated with a given user, ordered by id in descending order.
     * @param user The user whose transactions are to be found.
     * @param pageable The pagination information.
     * @return A page of transactions associated with the given user.
     */
    Page<Transaction> findAllByConnection_UserOrderByIdDesc(User user, Pageable pageable);

    /**
     * Delete a transaction by its connection id.
     * @param id The connection id of the transaction to be deleted.
     */
    void deleteByConnectionId(Long id);

    /**
     * Delete transactions by their connection id.
     * @param id The connection id of the transactions to be deleted.
     */
    void deleteTransactionsByConnection_Id(Long id);
}
