package com.LiqaaTech.Mappers;

import com.LiqaaTech.DTOs.RegistrationDTO;
import com.LiqaaTech.Entities.Registration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RegistrationMapper {

    public RegistrationDTO toDTO(Registration registration) {
        if (registration == null) {
            return null;
        }
        return RegistrationDTO.builder()
                .id(registration.getId())
                .eventId(registration.getEvent() != null ? registration.getEvent().getId() : null)
                .userId(registration.getUser() != null ? registration.getUser().getId() : null)
                .status(registration.getStatus())
                .registrationDate(registration.getRegistrationDate())
                .registeredAt(registration.getRegistrationDate())
                .build();
    }

    public Registration toEntity(RegistrationDTO dto) {
        if (dto == null) {
            return null;
        }
        Registration registration = new Registration();
        registration.setStatus(dto.getStatus());
        registration.setRegistrationDate(dto.getRegistrationDate());
        return registration;
    }

    public void updateEntityFromDTO(RegistrationDTO dto, Registration registration) {
        if (dto == null || registration == null) {
            return;
        }
        registration.setStatus(dto.getStatus());
        registration.setRegistrationDate(dto.getRegistrationDate());
    }

    public List<RegistrationDTO> toDTOList(List<Registration> registrations) {
        if (registrations == null) {
            return null;
        }
        return registrations.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Registration> toEntityList(List<RegistrationDTO> dtos) {
        if (dtos == null) {
            return null;
        }
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
} 