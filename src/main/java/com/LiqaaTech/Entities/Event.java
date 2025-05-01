package com.LiqaaTech.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
@SQLDelete(sql = "UPDATE events SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Event extends EntityBase {

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_public", nullable = false)
    private boolean isPublic = true;

    @Column(length = 50)
    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id", nullable = false)
    private User organizer;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Registration> registrations = new ArrayList<>();

    @Column(nullable = false)
    private boolean deleted = false;

    @Column(name = "max_participants")
    private Integer maxParticipants;

    @Column(name = "registration_deadline")
    private LocalDateTime registrationDeadline;

    public void addRegistration(Registration registration) {
        registrations.add(registration);
        registration.setEvent(this);
    }

    public void removeRegistration(Registration registration) {
        registrations.remove(registration);
        registration.setEvent(null);
    }
}