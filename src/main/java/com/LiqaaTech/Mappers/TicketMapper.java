package com.LiqaaTech.Mappers;

import com.LiqaaTech.DTOs.TicketDTO;
import com.LiqaaTech.Entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TicketMapper {
    @Autowired
    private RegistrationMapper registasMapper;

    public TicketDTO toDTO(Ticket ticket) {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setTicketCode(ticket.getTicketCode());
        ticketDTO.setRegistrationDTO(registasMapper.toDTO(ticket.getRegistration()));
        return ticketDTO;
    }
    public Ticket toEntity(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        ticket.setTicketCode(ticketDTO.getTicketCode());
        ticket.setRegistration(registasMapper.toEntity(ticketDTO.getRegistrationDTO()));
        return ticket;
    }

    public List<TicketDTO> toDTOList(List<Ticket> tickets) {
        return tickets.stream().map(this::toDTO).toList();
    }
    public List<Ticket> toEntityList(List<TicketDTO> ticketDTOS) {
        return ticketDTOS.stream().map(this::toEntity).toList();
    }

}
