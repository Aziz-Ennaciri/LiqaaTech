package com.LiqaaTech.Entities;

import com.LiqaaTech.Enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends EntityBase {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean enabled = true;

    @OneToMany(mappedBy = "organizer")
    private List<Event> organizedEvents;

    @OneToMany(mappedBy = "participant")
    private List<Registration> registrations;
}