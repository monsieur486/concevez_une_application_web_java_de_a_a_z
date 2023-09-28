package com.paymybuddy.paymybuddy.repository;


import com.paymybuddy.paymybuddy.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);

    Boolean existsByEmail(String email);
}
