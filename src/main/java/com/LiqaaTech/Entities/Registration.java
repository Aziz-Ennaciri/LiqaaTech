package com.LiqaaTech.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "registrations")
public class Registration extends EntityBase {

    @ManyToOne
    @JoinColumn(name = "participant_id")
    private User participant;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    private LocalDateTime registeredAt;
    private boolean inWaitingList = false;

    @OneToOne(mappedBy = "registration", cascade = CascadeType.ALL)
    private Ticket ticket;
}