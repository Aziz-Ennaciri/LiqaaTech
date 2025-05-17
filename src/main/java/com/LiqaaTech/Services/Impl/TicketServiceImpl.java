package com.LiqaaTech.Services.Impl;

import com.LiqaaTech.DTOs.TicketDTO;
import com.LiqaaTech.Entities.Event;
import com.LiqaaTech.Entities.Registration;
import com.LiqaaTech.Entities.Ticket;
import com.LiqaaTech.Repositories.TicketRepository;
import com.LiqaaTech.Services.Interf.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {
    
    private final TicketRepository ticketRepository;
    private static final String DEFAULT_STATUS = "ACTIVE";

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public List<TicketDTO> getAllTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(TicketDTO::new)
                .toList();
    }

    @Override
    public List<TicketDTO> getTicketsByUserId(Long userId) {
        return ticketRepository.findByRegistrationUserId(userId)
                .stream()
                .map(TicketDTO::new)
                .toList();
    }

    @Override
    public List<TicketDTO> getTicketsByEventId(Long eventId) {
        return ticketRepository.findByRegistrationEventId(eventId)
                .stream()
                .map(TicketDTO::new)
                .toList();
    }

    @Override
    public TicketDTO createTicket(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        Registration registration = new Registration();
        registration.setId(ticketDTO.getRegistrationId());
        ticket.setRegistration(registration);
        
        Event event = new Event();
        event.setId(ticketDTO.getEventId());
        ticket.setEvent(event);
        
        ticket.setTicketNumber(ticketDTO.getTicketNumber());
        ticket.setTicketType(ticketDTO.getTicketType());
        ticket.setPrice(ticketDTO.getPrice());
        ticket.setStatus(ticketDTO.getStatus());
        ticket.setTicketCode(ticketDTO.getTicketCode());
        ticket.setIsUsed(ticketDTO.getIsUsed());
        ticket.setUsedAt(ticketDTO.getUsedAt());
        
        if (ticket.getStatus() == null) {
            ticket.setStatus(DEFAULT_STATUS);
        }
        
        return new TicketDTO(ticketRepository.save(ticket));
    }

    @Override
    public TicketDTO updateTicket(Long id, TicketDTO ticketDTO) {
        Ticket existingTicket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        Registration registration = new Registration();
        registration.setId(ticketDTO.getRegistrationId());
        existingTicket.setRegistration(registration);

        existingTicket.setTicketNumber(ticketDTO.getTicketNumber());
        existingTicket.setTicketType(ticketDTO.getTicketType());
        existingTicket.setPrice(ticketDTO.getPrice());
        existingTicket.setStatus(ticketDTO.getStatus());
        existingTicket.setTicketCode(ticketDTO.getTicketCode());
        existingTicket.setIsUsed(ticketDTO.getIsUsed());
        existingTicket.setUsedAt(ticketDTO.getUsedAt());

        return new TicketDTO(ticketRepository.save(existingTicket));
    }

    @Override
    public void deleteTicket(Long ticketId) {
        ticketRepository.deleteById(ticketId);
    }

    @Override
    public TicketDTO getTicketById(Long ticketId) {
        return new TicketDTO(ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found")));
    }

    @Override
    public void cancelTicket(Long ticketId) {
        Ticket ticket = getTicketById(ticketId).getTicket();
        if (ticket == null) {
            throw new RuntimeException("Ticket not found");
        }
        if (ticket.getStatus().equals("CANCELLED")) {
            throw new RuntimeException("Ticket already cancelled");
        }
        ticket.setStatus("CANCELLED");
        ticketRepository.save(ticket);
    }
}