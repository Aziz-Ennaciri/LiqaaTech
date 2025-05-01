package com.LiqaaTech.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "registrations")
@SQLDelete(sql = "UPDATE registrations SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Registration extends EntityBase {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id", nullable = false)
    private User participant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(name = "registered_at", nullable = false)
    private LocalDateTime registeredAt;

    @Column(name = "in_waiting_list", nullable = false)
    private boolean inWaitingList = false;

    @OneToOne(mappedBy = "registration", cascade = CascadeType.ALL, orphanRemoval = true)
    private Ticket ticket;

    @Column(nullable = false)
    private boolean deleted = false;

    @Column(name = "attendance_status")
    @Enumerated(EnumType.STRING)
    private AttendanceStatus attendanceStatus;

    @Column(name = "check_in_time")
    private LocalDateTime checkInTime;

    public void setTicket(Ticket ticket) {
        if (ticket != null) {
            ticket.setRegistration(this);
        }
        this.ticket = ticket;
    }

    public enum AttendanceStatus {
        REGISTERED,
        CHECKED_IN,
        NO_SHOW,
        CANCELLED
    }

    protected void onCreate() {
        super.onCreate();
        if (registeredAt == null) {
            registeredAt = LocalDateTime.now();
        }
        if (attendanceStatus == null) {
            attendanceStatus = AttendanceStatus.REGISTERED;
        }
    }
}