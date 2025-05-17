package com.LiqaaTech.Enums;

public enum Role {
    ROLE_USER,
    ROLE_ORGANIZER,
    ROLE_ADMIN;

    @Override
    public String toString() {
        return this.name();
    }
} 