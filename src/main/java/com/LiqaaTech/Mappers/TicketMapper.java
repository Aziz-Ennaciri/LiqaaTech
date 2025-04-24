package com.LiqaaTech.Mappers;

import com.LiqaaTech.DTOs.TicketDTO;
import com.LiqaaTech.Entities.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {
    public TicketDTO toDTO(Ticket ticket) {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setTicketCode(ticket.getTicketCode());
        return ticketDTO;
    }
    public Ticket toEntity(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        ticket.setTicketCode(ticketDTO.getTicketCode());
        return ticket;
    }
}
