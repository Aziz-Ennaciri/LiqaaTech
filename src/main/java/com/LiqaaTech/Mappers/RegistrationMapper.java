package com.LiqaaTech.Mappers;

import com.LiqaaTech.DTOs.RegistrationDTO;
import com.LiqaaTech.Entities.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RegistrationMapper {
    @Autowired
    @Lazy
    private EventMapper eventMapper;
    @Autowired
    @Lazy
    private UserMapper userMapper;
    @Autowired
    @Lazy
    private TicketMapper ticketMapper;

    public RegistrationDTO toDTO(Registration registration) {
        if (registration == null) {
            return null;
        }

        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setParticipantDTO(userMapper.toDTOWithoutEvents(registration.getParticipant()));
        registrationDTO.setEventDTO(eventMapper.toDTOWithoutRegistrations(registration.getEvent()));
        registrationDTO.setRegisteredAt(registration.getRegisteredAt());
        registrationDTO.setInWaitingList(registration.isInWaitingList());

        if (registration.getTicket() != null) {
            registrationDTO.setTicketDTO(ticketMapper.toDTO(registration.getTicket()));
        }

        return registrationDTO;
    }

    public Registration toEntity(RegistrationDTO registrationDTO) {
        if (registrationDTO == null) {
            return null;
        }

        Registration registration = new Registration();
        registration.setParticipant(userMapper.toEntityWithoutEvents(registrationDTO.getParticipantDTO()));
        registration.setEvent(eventMapper.toEntity(registrationDTO.getEventDTO()));
        registration.setRegisteredAt(registrationDTO.getRegisteredAt());
        registration.setInWaitingList(registrationDTO.isInWaitingList());

        if (registrationDTO.getTicketDTO() != null) {
            registration.setTicket(ticketMapper.toEntity(registrationDTO.getTicketDTO()));
        }

        return registration;
    }

    public List<RegistrationDTO> toDTOList(List<Registration> registrations) {
        if (registrations == null) {
            return new ArrayList<>();
        }
        return registrations.stream().map(this::toDTO).toList();
    }

    public List<Registration> toEntityList(List<RegistrationDTO> dtos) {
        if (dtos == null) {
            return new ArrayList<>();
        }
        return dtos.stream().map(this::toEntity).toList();
    }

    public List<RegistrationDTO> toDTOListWithoutEvent(List<Registration> registrations) {
        if (registrations == null) {
            return new ArrayList<>();
        }
        return registrations.stream().map(this::toDTOWithoutEvent).toList();
    }

    public List<Registration> toEntityListWithoutEvent(List<RegistrationDTO> dtos) {
        if (dtos == null) {
            return new ArrayList<>();
        }
        return dtos.stream().map(this::toEntityWithoutEvent).toList();
    }

    public List<RegistrationDTO> toDTOListWithoutUser(List<Registration> registrations) {
        if (registrations == null) {
            return new ArrayList<>();
        }
        return registrations.stream().map(this::toDTOWithoutUser).toList();
    }

    public List<Registration> toEntityListWithoutUser(List<RegistrationDTO> dtos) {
        if (dtos == null) {
            return new ArrayList<>();
        }
        return dtos.stream().map(this::toEntityWithoutUser).toList();
    }

    private RegistrationDTO toDTOWithoutEvent(Registration registration) {
        if (registration == null) {
            return null;
        }

        RegistrationDTO dto = new RegistrationDTO();
        dto.setParticipantDTO(userMapper.toDTOWithoutEvents(registration.getParticipant()));
        dto.setRegisteredAt(registration.getRegisteredAt());
        dto.setInWaitingList(registration.isInWaitingList());

        if (registration.getTicket() != null) {
            dto.setTicketDTO(ticketMapper.toDTO(registration.getTicket()));
        }

        return dto;
    }

    private Registration toEntityWithoutEvent(RegistrationDTO dto) {
        if (dto == null) {
            return null;
        }

        Registration registration = new Registration();
        registration.setParticipant(userMapper.toEntityWithoutEvents(dto.getParticipantDTO()));
        registration.setRegisteredAt(dto.getRegisteredAt());
        registration.setInWaitingList(dto.isInWaitingList());

        if (dto.getTicketDTO() != null) {
            registration.setTicket(ticketMapper.toEntity(dto.getTicketDTO()));
        }

        return registration;
    }

    private RegistrationDTO toDTOWithoutUser(Registration registration) {
        if (registration == null) {
            return null;
        }

        RegistrationDTO dto = new RegistrationDTO();
        dto.setEventDTO(eventMapper.toDTOWithoutRegistrations(registration.getEvent()));
        dto.setRegisteredAt(registration.getRegisteredAt());
        dto.setInWaitingList(registration.isInWaitingList());

        if (registration.getTicket() != null) {
            dto.setTicketDTO(ticketMapper.toDTO(registration.getTicket()));
        }

        return dto;
    }

    private Registration toEntityWithoutUser(RegistrationDTO dto) {
        if (dto == null) {
            return null;
        }

        Registration registration = new Registration();
        registration.setEvent(eventMapper.toEntity(dto.getEventDTO()));
        registration.setRegisteredAt(dto.getRegisteredAt());
        registration.setInWaitingList(dto.isInWaitingList());

        if (dto.getTicketDTO() != null) {
            registration.setTicket(ticketMapper.toEntity(dto.getTicketDTO()));
        }

        return registration;
    }
}