package com.LiqaaTech.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDTO {
    private UserDTO participantDTO;
    private EventDTO eventDTO;
    private LocalDateTime registeredAt;
    private boolean inWaitingList = false;
    private TicketDTO ticketDTO;
}