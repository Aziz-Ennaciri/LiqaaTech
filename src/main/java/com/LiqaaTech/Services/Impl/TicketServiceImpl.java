package com.LiqaaTech.Services.Impl;

import com.LiqaaTech.DTOs.TicketDTO;
import com.LiqaaTech.Entities.Ticket;
import com.LiqaaTech.Mappers.RegistrationMapper;
import com.LiqaaTech.Mappers.TicketMapper;
import com.LiqaaTech.Repositories.TicketRepository;
import com.LiqaaTech.Services.Interf.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TicketMapper ticketMapper;
    @Autowired
    private RegistrationMapper registrationMapper;

    @Override
    public TicketDTO createTicket(TicketDTO ticketDTO) {
        Ticket ticket = ticketMapper.toEntity(ticketDTO);
        Ticket savedTicket = ticketRepository.save(ticket);
        return ticketMapper.toDTO(savedTicket);
    }

    @Override
    public TicketDTO updateTicket(Long ticketId, TicketDTO ticketDTO) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found with id: " + ticketId));
        ticket.setTicketCode(ticketDTO.getTicketCode());
        if(ticketDTO.getRegistrationDTO() != null) {
            ticket.setRegistration(registrationMapper.toEntity(ticketDTO.getRegistrationDTO()));
        }
        Ticket updatedTicket = ticketRepository.save(ticket);
        return ticketMapper.toDTO(updatedTicket);
    }

    @Override
    public List<TicketDTO> getAllTickets(Long eventId, Long registrationId) {
        List<Ticket> tickets = ticketRepository.findByEventIdAndRegistrationId(eventId, registrationId);
        if(tickets.isEmpty()) {
            throw new RuntimeException("No tickets found");
        }
        return ticketMapper.toDTOList(tickets);
    }

    @Override
    public TicketDTO getTicketById(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found with id: " + ticketId));
        return ticketMapper.toDTO(ticket);
    }

    @Override
    public void deleteTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found with id: " + ticketId));
        ticketRepository.delete(ticket);
    }
}
