package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository is an interface that extends CrudRepository.
 * It provides methods to perform CRUD operations on User entities.
 * It also provides additional methods to find a User by email and check if a User exists by email.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Finds a User entity by its email.
     *
     * @param email the email of the User entity to be found.
     * @return the User entity with the given email or null if no such User entity exists.
     */
    User findByEmail(String email);

    /**
     * Checks if a User entity exists by its email.
     *
     * @param email the email of the User entity to be checked.
     * @return true if a User entity with the given email exists, false otherwise.
     */
    Boolean existsByEmail(String email);
}