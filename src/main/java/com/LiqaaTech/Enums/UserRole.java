package com.LiqaaTech.Enums;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum UserRole {
    ROLE_USER,
    ROLE_ORGANIZER,
    ROLE_ADMIN;

    @Override
    public String toString() {
        return this.name();
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + this.name()));
    }
}
