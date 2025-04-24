package com.LiqaaTech.DTOs;

import com.LiqaaTech.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String email;
    private String username;
    private Role role;
    private boolean enabled;
    private List<EventDTO> organizedEventsDTO;
    private List<RegistrationDTO> registrationsDTO;
}
