package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.entity.Transaction;
import com.paymybuddy.paymybuddy.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Transaction findById(long id);

    List<Transaction> findAllByConnection_UserOrderByIdDesc(User user, Pageable pageable);
}
