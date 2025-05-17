package com.LiqaaTech.Mappers;

import com.LiqaaTech.DTOs.TicketDTO;
import com.LiqaaTech.Entities.Event;
import com.LiqaaTech.Entities.Ticket;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TicketMapper {

    public TicketDTO toDTO(Ticket ticket) {
        if (ticket == null) {
            return null;
        }
        return TicketDTO.builder()
                .id(ticket.getId())
                .registrationId(ticket.getRegistration() != null ? ticket.getRegistration().getId() : null)
                .ticketNumber(ticket.getTicketNumber())
                .ticketType("REGULAR") // Default type since it's not in entity
                .price(ticket.getPrice())
                .status(ticket.getStatus())
                .ticketCode(ticket.getTicketNumber()) // Using ticketNumber as code
                .issuedAt(ticket.getUsedAt()) // Using usedAt as issuedAt
                .isUsed(ticket.getIsUsed())
                .usedAt(ticket.getUsedAt())
                .createdAt(ticket.getCreatedAt())
                .updatedAt(ticket.getUpdatedAt())
                .eventName(ticket.getEvent() != null ? ticket.getEvent().getTitle() : null)
                .eventDate(ticket.getEvent() != null ? ticket.getEvent().getDateTime() : null)
                .build();
    }

    public Ticket toEntity(TicketDTO dto) {
        if (dto == null) {
            return null;
        }
        Ticket ticket = new Ticket();
        ticket.setId(dto.getId());
        ticket.setTicketNumber(dto.getTicketNumber());
        ticket.setPrice(dto.getPrice());
        ticket.setStatus(dto.getStatus());
        ticket.setIsUsed(dto.getIsUsed());
        ticket.setUsedAt(dto.getUsedAt());
        return ticket;
    }

    public void updateEntityFromDTO(TicketDTO dto, Ticket ticket) {
        if (dto == null || ticket == null) {
            return;
        }
        ticket.setTicketNumber(dto.getTicketNumber());
        ticket.setPrice(dto.getPrice());
        ticket.setStatus(dto.getStatus());
        ticket.setIsUsed(dto.getIsUsed());
        ticket.setUsedAt(dto.getUsedAt());
    }

    public List<TicketDTO> toDTOList(List<Ticket> tickets) {
        if (tickets == null) {
            return null;
        }
        return tickets.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Ticket> toEntityList(List<TicketDTO> dtos) {
        if (dtos == null) {
            return null;
        }
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
} 