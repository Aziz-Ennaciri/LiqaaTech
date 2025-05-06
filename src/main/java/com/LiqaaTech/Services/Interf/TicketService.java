package com.LiqaaTech.Services.Interf;

import com.LiqaaTech.DTOs.TicketDTO;
import java.util.List;

public interface TicketService {
    TicketDTO createTicket(TicketDTO ticketDTO);
    TicketDTO updateTicket(Long ticketId, TicketDTO ticketDTO);
    List<TicketDTO> getAllTickets();
    TicketDTO getTicketById(Long ticketId);
    void deleteTicket(Long ticketId);
    List<TicketDTO> getTicketsByEventId(Long eventId);
    List<TicketDTO> getTicketsByUserId(Long userId);
}
