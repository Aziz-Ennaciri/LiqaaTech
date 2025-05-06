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
                .registeredAt(registration.getRegisteredAt())
                .inWaitingList(registration.isInWaitingList())
                .attendanceStatus(registration.getAttendanceStatus() != null ? 
                    registration.getAttendanceStatus().name() : null)
                .checkInTime(registration.getCheckInTime())
                .build();
    }

    public Registration toEntity(RegistrationDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Registration registration = new Registration();
        registration.setRegisteredAt(dto.getRegisteredAt());
        registration.setInWaitingList(dto.isInWaitingList());
        registration.setCheckInTime(dto.getCheckInTime());
        return registration;
    }

    public void updateEntityFromDTO(RegistrationDTO dto, Registration entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setRegisteredAt(dto.getRegisteredAt());
        entity.setInWaitingList(dto.isInWaitingList());
        entity.setCheckInTime(dto.getCheckInTime());
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