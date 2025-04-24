package com.LiqaaTech.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    private String title;
    private String location;
    private LocalDateTime date;
    private String description;
    private String imageUrl;
    private boolean isPublic = true;
    private String category;
    private UserDTO organizerDTO;
    private List<RegistrationDTO> registrationsDTO;
}
