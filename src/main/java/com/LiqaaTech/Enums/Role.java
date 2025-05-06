package com.LiqaaTech.Enums;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum Role {
    PARTICIPANT(Set.of(Permission.READ_EVENTS, Permission.REGISTER_EVENT)),
    ORGANIZER(Set.of(Permission.READ_EVENTS, Permission.REGISTER_EVENT, Permission.CREATE_EVENT, Permission.UPDATE_EVENT, Permission.DELETE_EVENT)),
    ADMIN(Set.of(Permission.values()));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + this.name()));
    }
}
