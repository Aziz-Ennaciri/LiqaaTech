package com.LiqaaTech.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {
    private String ticketCode;
    private RegistrationDTO registrationDTO;
}
