package com.LiqaaTech.Services.Interf;

import com.LiqaaTech.DTOs.RegistrationDTO;

import java.util.List;

public interface RegistrationService {
    RegistrationDTO createRegistration(RegistrationDTO registrationDTO);
    List<RegistrationDTO> getAllRegistrations(Long eventId);
    RegistrationDTO getRegistrationById(Long eventId, Long registrationId);
    RegistrationDTO updateRegistration(Long registrationId, RegistrationDTO registrationDTO);
    void deleteRegistration(Long registrationId);
}
