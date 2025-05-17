package com.LiqaaTech.Services;

import com.LiqaaTech.Entities.Ticket;
import com.LiqaaTech.DTOs.TicketDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TicketService {
    
    // CRUD Operations
    Ticket createTicket(TicketDTO ticketDTO);
    Ticket updateTicket(Long id, TicketDTO ticketDTO);
    void deleteTicket(Long id);
    Ticket getTicketById(Long id);
    
    // Query Methods
    List<Ticket> getTicketsByUserId(Long userId);
    List<Ticket> getTicketsByEventId(Long eventId);
    List<Ticket> getTicketsByStatus(String status);
    
    // Pagination
    Page<Ticket> getTicketsByUserId(Long userId, Pageable pageable);
    Page<Ticket> getTicketsByEventId(Long eventId, Pageable pageable);
    
    // Ticket Operations
    Ticket checkInTicket(Long ticketId);
    Ticket checkOutTicket(Long ticketId);
    Ticket cancelTicket(Long ticketId, String reason);
    


}
