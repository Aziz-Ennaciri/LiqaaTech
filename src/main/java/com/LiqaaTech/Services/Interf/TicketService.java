package com.LiqaaTech.Services.Interf;

import com.LiqaaTech.DTOs.TicketDTO;
import java.util.List;

public interface TicketService {
    List<TicketDTO> getAllTickets();
    List<TicketDTO> getTicketsByUserId(Long userId);
    List<TicketDTO> getTicketsByEventId(Long eventId);
    TicketDTO createTicket(TicketDTO ticketDTO);
    void cancelTicket(Long ticketId);
    TicketDTO getTicketById(Long ticketId);
    void deleteTicket(Long ticketId);
    TicketDTO updateTicket(Long id, TicketDTO ticketDTO);
}
