package com.LiqaaTech.DTOs;

import com.LiqaaTech.Enums.UserRole;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class SignupRequest {
    private String username;
    private String email;
    private String password;
    private Set<UserRole> roles = new HashSet<>();
} 