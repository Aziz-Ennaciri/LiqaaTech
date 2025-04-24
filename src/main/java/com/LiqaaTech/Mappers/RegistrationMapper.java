package com.LiqaaTech.Mappers;

import com.LiqaaTech.DTOs.RegistrationDTO;
import com.LiqaaTech.Entities.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RegistrationMapper {
    @Autowired
    private EventMapper EventMapper;
    @Autowired
    private UserMapper UserMapper;
    @Autowired
    private TicketMapper TicketMapper;

    public RegistrationDTO toDTO(Registration registration) {
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setParticipantDTO(UserMapper.toDTO(registration.getParticipant()));
        registrationDTO.setEventDTO(EventMapper.toDTO(registration.getEvent()));
        registrationDTO.setRegisteredAt(registration.getRegisteredAt());
        registrationDTO.setInWaitingList(registration.isInWaitingList());
        registrationDTO.setTicketDTO(TicketMapper.toDTO(registration.getTicket()));
        return registrationDTO;
    }
    public Registration toEntity(RegistrationDTO registrationDTO) {
        Registration registration = new Registration();
        registration.setParticipant(UserMapper.toEntity(registrationDTO.getParticipantDTO()));
        registration.setEvent(EventMapper.toEntity(registrationDTO.getEventDTO()));
        registration.setRegisteredAt(registrationDTO.getRegisteredAt());
        registration.setInWaitingList(registrationDTO.isInWaitingList());
        registration.setTicket(TicketMapper.toEntity(registrationDTO.getTicketDTO()));
        return registration;
    }

    public List<RegistrationDTO> toDTOList(List<Registration> registrations) {
        return registrations.stream().map(this::toDTO).toList();
    }

    public List<Registration> toEntityList(List<RegistrationDTO> dtos) {
        return dtos.stream().map(this::toEntity).toList();
    }

}
