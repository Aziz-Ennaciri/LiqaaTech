package com.LiqaaTech.Mappers;

import com.LiqaaTech.DTOs.TicketDTO;
import com.LiqaaTech.Entities.Ticket;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TicketMapper {

    public TicketDTO toDTO(Ticket ticket) {
        return toDTOWithoutRelations(ticket);
    }

    public TicketDTO toDTOWithoutRelations(Ticket ticket) {
        if (ticket == null) {
            return null;
        }

        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setTicketCode(ticket.getTicketCode());
        return ticketDTO;
    }

    public Ticket toEntity(TicketDTO ticketDTO) {
        return toEntityWithoutRelations(ticketDTO);
    }

    public Ticket toEntityWithoutRelations(TicketDTO ticketDTO) {
        if (ticketDTO == null) {
            return null;
        }

        Ticket ticket = new Ticket();
        ticket.setTicketCode(ticketDTO.getTicketCode());
        return ticket;
    }

    public void updateEntityFromDTO(TicketDTO dto, Ticket entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setTicketCode(dto.getTicketCode());
    }

    public List<TicketDTO> toDTOList(List<Ticket> tickets) {
        if (tickets == null) {
            return null;
        }
        return tickets.stream().map(this::toDTO).toList();
    }

    public List<Ticket> toEntityList(List<TicketDTO> ticketDTOs) {
        if (ticketDTOs == null) {
            return null;
        }
        return ticketDTOs.stream().map(this::toEntity).toList();
    }
}