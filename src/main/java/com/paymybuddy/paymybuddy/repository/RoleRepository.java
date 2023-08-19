package com.paymybuddy.paymybuddy.repository;


import com.paymybuddy.paymybuddy.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
