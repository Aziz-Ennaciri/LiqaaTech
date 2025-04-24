package com.LiqaaTech.Services.Impl;

import com.LiqaaTech.DTOs.RegistrationDTO;
import com.LiqaaTech.Services.Interf.RegistrationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    @Override
    public RegistrationDTO createRegistration(RegistrationDTO registrationDTO) {
        return null;
    }

    @Override
    public List<RegistrationDTO> getAllRegistrations(Long eventId) {
        return null;
    }

    @Override
    public RegistrationDTO getRegistrationById(Long eventId, Long registrationId) {
        return null;
    }

    @Override
    public RegistrationDTO updateRegistration(Long registrationId, RegistrationDTO registrationDTO) {
        return null;
    }

    @Override
    public void deleteRegistration(Long registrationId) {

    }
}
