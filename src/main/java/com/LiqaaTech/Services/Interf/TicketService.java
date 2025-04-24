package com.LiqaaTech.Services.Interf;


import com.LiqaaTech.DTOs.TicketDTO;

import java.util.List;

public interface TicketService {
    TicketDTO createTicket(TicketDTO ticketDTO);
    List<TicketDTO> getAllTickets(Long eventId, Long registrationId);
    TicketDTO getTicketById(Long ticketId);
    void deleteTicket(Long ticketId);
}
