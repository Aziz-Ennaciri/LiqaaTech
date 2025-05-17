package com.LiqaaTech.Seeder;

import com.LiqaaTech.Entities.Role;
import com.LiqaaTech.Enums.UserRole;
import com.LiqaaTech.Repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        for (UserRole userRole : UserRole.values()) {
            roleRepository.findByName(userRole)
                .orElseGet(() -> roleRepository.save(new Role(userRole)));
        }
    }
} 