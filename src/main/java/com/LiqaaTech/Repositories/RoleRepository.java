package com.LiqaaTech.Repositories;

import com.LiqaaTech.Entities.Role;
import com.LiqaaTech.Enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findFirstByName(UserRole name);
    Optional<Role> findByName(UserRole name);
} 