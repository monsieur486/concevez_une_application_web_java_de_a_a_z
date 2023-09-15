package com.paymybuddy.paymybuddy.repository;


import com.paymybuddy.paymybuddy.entity.Connection;
import com.paymybuddy.paymybuddy.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {

    List<Connection> findByUserOrderByNickname(User user);

    Page<Connection> findByUserOrderByNickname(User use, Pageable pageable);

    Connection findById(long id);
}
