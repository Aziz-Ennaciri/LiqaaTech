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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RegistrationServiceImpl implements RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final RegistrationMapper registrationMapper;
    private final UserMapper userMapper;
    private final EventMapper eventMapper;
    private final TicketMapper ticketMapper;

    @Autowired
    public RegistrationServiceImpl(RegistrationRepository registrationRepository,
                                   RegistrationMapper registrationMapper,
                                   UserMapper userMapper,
                                   EventMapper eventMapper,
                                   TicketMapper ticketMapper) {
        this.registrationRepository = registrationRepository;
        this.registrationMapper = registrationMapper;
        this.userMapper = userMapper;
        this.eventMapper = eventMapper;
        this.ticketMapper = ticketMapper;
    }

    @Override
    public RegistrationDTO createRegistration(RegistrationDTO registrationDTO) {
        if (registrationDTO == null) {
            throw new IllegalArgumentException("Registration data cannot be null");
        }

        Registration registration = registrationMapper.toEntity(registrationDTO);
        Registration savedRegistration = registrationRepository.save(registration);
        return registrationMapper.toDTO(savedRegistration);
    }

    @Override
    public List<RegistrationDTO> getAllRegistrations(Long eventId) {
        if (eventId == null) {
            throw new IllegalArgumentException("Event ID cannot be null");
        }

        List<Registration> registrations = registrationRepository.findByEventId(eventId);
        if(registrations.isEmpty()) {
            throw new NotFoundException("No registrations found for event: " + eventId);
        }
        return registrationMapper.toDTOList(registrations);
    }

    @Override
    public RegistrationDTO getRegistrationById(Long eventId, Long registrationId) {
        if (eventId == null || registrationId == null) {
            throw new IllegalArgumentException("Event ID and Registration ID cannot be null");
        }

        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new NotFoundException("Registration not found with id: " + registrationId));



        return registrationMapper.toDTO(registration);
    }

    @Override
    public RegistrationDTO updateRegistration(Long registrationId, RegistrationDTO registrationDTO) {
        if (registrationId == null || registrationDTO == null) {
            throw new IllegalArgumentException("Registration ID and data cannot be null");
        }

        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new NotFoundException("Registration not found with id: " + registrationId));

        registration.setParticipant(userMapper.toEntity(registrationDTO.getParticipantDTO()));
        registration.setEvent(eventMapper.toEntity(registrationDTO.getEventDTO()));
        registration.setRegisteredAt(registrationDTO.getRegisteredAt());
        registration.setInWaitingList(registrationDTO.isInWaitingList());

        if (registrationDTO.getTicketDTO() != null) {
            registration.setTicket(ticketMapper.toEntity(registrationDTO.getTicketDTO()));
        }

        Registration updatedRegistration = registrationRepository.save(registration);
        return registrationMapper.toDTO(updatedRegistration);
    }

    @Override
    public void deleteRegistration(Long registrationId) {
        if (registrationId == null) {
            throw new IllegalArgumentException("Registration ID cannot be null");
        }

        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new NotFoundException("Registration not found with id: " + registrationId));
        registrationRepository.delete(registration);
    }
}