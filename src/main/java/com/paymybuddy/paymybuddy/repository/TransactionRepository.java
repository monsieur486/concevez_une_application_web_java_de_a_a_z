package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.entity.Transaction;
import com.paymybuddy.paymybuddy.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    Transaction findById(long id);

    Page<Transaction> findAllByConnection_UserOrderByIdDesc(User user, Pageable pageable);

    void deleteByConnectionId(Long id);

    void deleteTransactionsByConnection_Id(Long id);
}
