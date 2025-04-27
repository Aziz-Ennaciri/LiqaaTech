package com.LiqaaTech.Services.Impl;

import com.LiqaaTech.DTOs.RegistrationDTO;
import com.LiqaaTech.Entities.Registration;
import com.LiqaaTech.Exceptions.NotFoundException;
import com.LiqaaTech.Mappers.EventMapper;
import com.LiqaaTech.Mappers.RegistrationMapper;
import com.LiqaaTech.Mappers.TicketMapper;
import com.LiqaaTech.Mappers.UserMapper;
import com.LiqaaTech.Repositories.RegistrationRepository;
import com.LiqaaTech.Services.Interf.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    private RegistrationMapper registrationMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private TicketMapper ticketMapper;

    @Override
    public RegistrationDTO createRegistration(RegistrationDTO registrationDTO) {
        Registration registration = registrationMapper.toEntity(registrationDTO);
        Registration savedRegistration = registrationRepository.save(registration);
        return registrationMapper.toDTO(savedRegistration);
    }

    @Override
    public List<RegistrationDTO> getAllRegistrations(Long eventId) {
        List<Registration> registrations = registrationRepository.findByEventId(eventId);
        if(registrations.isEmpty()) {
            throw new NotFoundException("No registrations found");
        }
        return registrationMapper.toDTOList(registrations);
    }

    @Override
    public RegistrationDTO getRegistrationById(Long eventId, Long registrationId) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new NotFoundException("Registration not found"));
        return registrationMapper.toDTO(registration);
    }

    @Override
    public RegistrationDTO updateRegistration(Long registrationId, RegistrationDTO registrationDTO) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new NotFoundException("Registration not found"));
        registration.setParticipant(userMapper.toEntity(registrationDTO.getParticipantDTO()));
        registration.setEvent(eventMapper.toEntity(registrationDTO.getEventDTO()));
        registration.setRegisteredAt(registrationDTO.getRegisteredAt());
        registration.setInWaitingList(registrationDTO.isInWaitingList());
        registration.setTicket(ticketMapper.toEntity(registrationDTO.getTicketDTO()));
        Registration updatedRegistration = registrationRepository.save(registration);
        return registrationMapper.toDTO(updatedRegistration);
    }

    @Override
    public void deleteRegistration(Long registrationId) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new NotFoundException("Registration not found"));
        registrationRepository.delete(registration);
    }
}
