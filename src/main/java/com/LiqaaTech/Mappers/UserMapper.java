package com.LiqaaTech.Mappers;

import com.LiqaaTech.DTOs.UserDTO;
import com.LiqaaTech.Entities.User;
import com.LiqaaTech.Entities.Role;
import com.LiqaaTech.Enums.UserRole;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEnabled(user.isEnabled());
        
        // Convert roles to strings
        if (user.getRoles() != null) {
            Set<String> roleStrings = user.getRoles().stream()
                    .map(role -> role.getName().toString())
                    .collect(Collectors.toSet());
            dto.setRoles(roleStrings);
        }

        return dto;
    }

    public User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEnabled(dto.getEnabled() != null ? dto.getEnabled() : true);

        // Convert string roles to Role entities
        if (dto.getRoles() != null) {
            Set<Role> roles = dto.getRoles().stream()
                    .map(roleName -> new Role(UserRole.valueOf(roleName)))
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }

        return user;
    }

    public void updateEntityFromDTO(UserDTO dto, User user) {
        if (dto == null || user == null) {
            return;
        }

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        if (dto.getEnabled() != null) {
            user.setEnabled(dto.getEnabled());
        }

        if (dto.getRoles() != null) {
            Set<Role> roles = dto.getRoles().stream()
                    .map(roleName -> new Role(UserRole.valueOf(roleName)))
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }
    }

    public UserDetails toUserDetails(User user) {
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName().toString()))
                        .collect(Collectors.toList()))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!user.isEnabled())
                .build();
    }
} 