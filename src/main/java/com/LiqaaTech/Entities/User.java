package com.LiqaaTech.Entities;

import com.LiqaaTech.Enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class User extends EntityBase {

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Role role = Role.PARTICIPANT;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(nullable = false)
    @Builder.Default
    private boolean deleted = false;

    @OneToMany(mappedBy = "organizer", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Event> organizedEvents = new ArrayList<>();

    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Registration> registrations = new ArrayList<>();

    @Column(length = 15)
    private String phoneNumber;

    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    public void addOrganizedEvent(Event event) {
        organizedEvents.add(event);
        event.setOrganizer(this);
    }

    public void removeOrganizedEvent(Event event) {
        organizedEvents.remove(event);
        event.setOrganizer(null);
    }

    public void addRegistration(Registration registration) {
        registrations.add(registration);
        registration.setParticipant(this);
    }

    public void removeRegistration(Registration registration) {
        registrations.remove(registration);
        registration.setParticipant(null);
    }
}