package com.LiqaaTech.Services.Impl;

import com.LiqaaTech.DTOs.TicketDTO;
import com.LiqaaTech.Entities.Ticket;
import com.LiqaaTech.Exceptions.NotFoundException;
import com.LiqaaTech.Mappers.RegistrationMapper;
import com.LiqaaTech.Mappers.TicketMapper;
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
    private final TicketMapper ticketMapper;
    private final RegistrationMapper registrationMapper;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository,
                             TicketMapper ticketMapper,
                             RegistrationMapper registrationMapper) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
        this.registrationMapper = registrationMapper;
    }

    @Override
    public TicketDTO createTicket(TicketDTO ticketDTO) {
        if (ticketDTO == null) {
            throw new IllegalArgumentException("Ticket data cannot be null");
        }

        Ticket ticket = ticketMapper.toEntity(ticketDTO);
        Ticket savedTicket = ticketRepository.save(ticket);
        return ticketMapper.toDTO(savedTicket);
    }

    @Override
    public TicketDTO updateTicket(Long ticketId, TicketDTO ticketDTO) {
        if (ticketId == null || ticketDTO == null) {
            throw new IllegalArgumentException("Ticket ID and data cannot be null");
        }

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NotFoundException("Ticket not found with id: " + ticketId));

        ticket.setTicketCode(ticketDTO.getTicketCode());
        if(ticketDTO.getRegistrationDTO() != null) {
            ticket.setRegistration(registrationMapper.toEntity(ticketDTO.getRegistrationDTO()));
        }

        Ticket updatedTicket = ticketRepository.save(ticket);
        return ticketMapper.toDTO(updatedTicket);
    }

    @Override
    public List<TicketDTO> getAllTickets(Long eventId, Long registrationId) {
        if (eventId == null || registrationId == null) {
            throw new IllegalArgumentException("Event ID and Registration ID cannot be null");
        }

        List<Ticket> tickets = ticketRepository.findByRegistration_Event_IdAndRegistration_Id(eventId, registrationId);
        if(tickets.isEmpty()) {
            throw new NotFoundException("No tickets found for event: " + eventId + " and registration: " + registrationId);
        }
        return ticketMapper.toDTOList(tickets);
    }

    @Override
    public TicketDTO getTicketById(Long ticketId) {
        if (ticketId == null) {
            throw new IllegalArgumentException("Ticket ID cannot be null");
        }

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NotFoundException("Ticket not found with id: " + ticketId));
        return ticketMapper.toDTO(ticket);
    }

    @Override
    public void deleteTicket(Long ticketId) {
        if (ticketId == null) {
            throw new IllegalArgumentException("Ticket ID cannot be null");
        }

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NotFoundException("Ticket not found with id: " + ticketId));
        ticketRepository.delete(ticket);
    }
}