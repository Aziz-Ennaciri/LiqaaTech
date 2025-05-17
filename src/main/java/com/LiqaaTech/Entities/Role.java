package com.LiqaaTech.Entities;

import com.LiqaaTech.Enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "roles")
public class Role extends EntityBase{

    @Enumerated(EnumType.STRING)
    @Column(length = 20, unique = true)
    private UserRole name;

    public Role() {}

    public Role(UserRole name) {
        this.name = name;
    }
} 