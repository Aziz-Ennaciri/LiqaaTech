package com.LiqaaTech.Services.Interf;

import com.LiqaaTech.DTOs.RegistrationDTO;

import java.util.List;

public interface RegistrationService {
    List<RegistrationDTO> getAllRegistrations();
    RegistrationDTO getRegistrationById(Long id);
    RegistrationDTO createRegistration(RegistrationDTO registrationDTO);
    RegistrationDTO updateRegistration(Long id, RegistrationDTO registrationDTO);
    void deleteRegistration(Long id);
    List<RegistrationDTO> getRegistrationsByUser(Long userId);
    List<RegistrationDTO> getRegistrationsByEvent(Long eventId);
    RegistrationDTO cancelRegistration(Long id);
    List<RegistrationDTO> getRegistrationsByEventId(Long eventId);
    List<RegistrationDTO> getRegistrationsByUserId(Long userId);
    int getTotalRegistrations();
}
