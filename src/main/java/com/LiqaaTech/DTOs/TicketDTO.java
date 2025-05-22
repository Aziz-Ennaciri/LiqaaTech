package com.LiqaaTech.DTOs;

import com.LiqaaTech.Entities.Ticket;
import com.LiqaaTech.Entities.Registration;
import com.LiqaaTech.Entities.Event;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketDTO {

    public TicketDTO(Ticket ticket) {
        if (ticket != null) {
            this.id = ticket.getId();
            this.registrationId = ticket.getRegistration() != null ? ticket.getRegistration().getId() : null;
            this.eventId = ticket.getEvent() != null ? ticket.getEvent().getId() : null;
            this.ticketNumber = ticket.getTicketNumber();
            this.ticketType = ticket.getTicketType();
            this.price = ticket.getPrice();
            this.status = ticket.getStatus();
            this.ticketCode = ticket.getTicketCode();
            this.isUsed = ticket.getIsUsed();
            this.usedAt = ticket.getUsedAt();
            this.createdAt = ticket.getCreatedAt();
            this.updatedAt = ticket.getUpdatedAt();
            this.eventName = ticket.getEvent() != null ? ticket.getEvent().getTitle() : null;
            this.eventDate = ticket.getEvent() != null ? ticket.getEvent().getStartDateTime() : null;
        }
    }

    public Ticket getTicket() {
        Ticket ticket = new Ticket();
        ticket.setId(this.id);
        if (this.registrationId != null) {
            Registration registration = new Registration();
            registration.setId(this.registrationId);
            ticket.setRegistration(registration);
        }
        if (this.eventId != null) {
            Event event = new Event();
            event.setId(this.eventId);
            ticket.setEvent(event);
        }
        ticket.setTicketNumber(this.ticketNumber);
        ticket.setTicketType(this.ticketType);
        ticket.setPrice(this.price);
        ticket.setStatus(this.status);
        ticket.setTicketCode(this.ticketCode);
        ticket.setIsUsed(this.isUsed);
        ticket.setUsedAt(this.usedAt);
        ticket.setCreatedAt(this.createdAt);
        ticket.setUpdatedAt(this.updatedAt);
        return ticket;
    }

    private Long id;
    private Long registrationId;
    private Long eventId;
    private String ticketNumber;
    private String ticketType;
    private Double price;
    private String status;
    private String ticketCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime issuedAt;
    private Boolean isUsed;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime usedAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    private String eventName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
}