package com.paymybuddy.paymybuddy.repository;


import com.paymybuddy.paymybuddy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
