package com.LiqaaTech.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "events")
public class Event extends EntityBase {

    @Column(nullable = false)
    private String title;
    private String location;
    private LocalDateTime date;

    @Lob
    private String description;
    private String imageUrl;
    private boolean isPublic = true;
    private String category;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Registration> registrations;
}