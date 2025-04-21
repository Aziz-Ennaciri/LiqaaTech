package com.LiqaaTech.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tickets")
public class Ticket extends EntityBase {

    @Column(nullable = false, unique = true)
    private String ticketCode;

    @OneToOne
    @JoinColumn(name = "registration_id")
    private Registration registration;
}