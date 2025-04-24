package com.LiqaaTech.Services.Impl;

import com.LiqaaTech.DTOs.TicketDTO;
import com.LiqaaTech.Services.Interf.TicketService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    @Override
    public TicketDTO createTicket(TicketDTO ticketDTO) {
        return null;
    }

    @Override
    public List<TicketDTO> getAllTickets(Long eventId, Long registrationId) {
        return null;
    }

    @Override
    public TicketDTO getTicketById(Long ticketId) {
        return null;
    }

    @Override
    public void deleteTicket(Long ticketId) {

    }
}
