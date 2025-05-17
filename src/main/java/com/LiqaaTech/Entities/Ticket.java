package com.LiqaaTech.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class Ticket extends EntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "registration_id", nullable = false)
    private Registration registration;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(name = "ticket_number", nullable = false, unique = true)
    private String ticketNumber;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "is_used", nullable = false)
    private Boolean isUsed = false;

    @Column(name = "used_at")
    private LocalDateTime usedAt;

    @Column(name = "qr_code", length = 1000)
    private String qrCode;

    @Column(name = "qr_code_image")
    @Lob
    private byte[] qrCodeImage;

    @Column(name = "ticket_type")
    private String ticketType;

    @Column(name = "seat_number")
    private String seatNumber;

    @Column(name = "gate_number")
    private String gateNumber;

    @Column(name = "zone")
    private String zone;





    @Column(name = "barcode")
    private String barcode;

    @Column(name = "ticket_code", unique = true)
    private String ticketCode;

    @Column(name = "check_in_time")
    private LocalDateTime checkInTime;

    @Column(name = "check_out_time")
    private LocalDateTime checkOutTime;

    @Column(name = "cancellation_reason")
    private String cancellationReason;

    @Column(name = "cancellation_time")
    private LocalDateTime cancellationTime;

    @Column(name = "refund_status")
    private String refundStatus;

    @Column(name = "event_id", nullable = false, insertable = false, updatable = false)
    private Long eventId;

    @Column(name = "user_id", nullable = false)
    private Long userId;



    @PrePersist
    protected void onCreate() {
        if (this.ticketNumber == null) {
            this.ticketNumber = "TICK-" + System.currentTimeMillis();
        }
        if (this.ticketCode == null) {
            this.ticketCode = "CODE-" + System.currentTimeMillis();
        }
        if (this.registration != null) {
            this.eventId = this.registration.getEvent().getId();
            this.userId = this.registration.getUser().getId();
            if (this.status == null) {
                this.status = "ACTIVE";
            }
            if (this.ticketType == null) {
                this.ticketType = "REGULAR";
            }
        }
    }

    @PreUpdate
    protected void onUpdate() {
        if (this.isUsed && this.usedAt == null) {
            this.usedAt = LocalDateTime.now();
        }
        if (this.status != null && this.status.equals("CANCELLED") && this.cancellationTime == null) {
            this.cancellationTime = LocalDateTime.now();
        }
    }
}