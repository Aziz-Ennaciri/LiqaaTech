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
import java.util.stream.Collectors;

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
    public List<RegistrationDTO> getAllRegistrations() {
        return registrationRepository.findAll().stream()
                .map(registrationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RegistrationDTO getRegistrationById(Long registrationId) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        return registrationMapper.toDTO(registration);
    }

    @Override
    public RegistrationDTO updateRegistration(Long registrationId, RegistrationDTO registrationDTO) {
        Registration existingRegistration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        
        registrationMapper.updateEntityFromDTO(registrationDTO, existingRegistration);
        Registration updatedRegistration = registrationRepository.save(existingRegistration);
        return registrationMapper.toDTO(updatedRegistration);
    }

    @Override
    public void deleteRegistration(Long registrationId) {
        registrationRepository.deleteById(registrationId);
    }

    @Override
    public List<RegistrationDTO> getRegistrationsByEventId(Long eventId) {
        return registrationRepository.findByEventId(eventId).stream()
                .map(registrationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RegistrationDTO> getRegistrationsByUserId(Long userId) {
        return registrationRepository.findByUserId(userId).stream()
                .map(registrationMapper::toDTO)
                .collect(Collectors.toList());
    }
}