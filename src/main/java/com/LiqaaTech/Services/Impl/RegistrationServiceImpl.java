package com.LiqaaTech.Services.Impl;

import com.LiqaaTech.DTOs.RegistrationDTO;
import com.LiqaaTech.Entities.Event;
import com.LiqaaTech.Entities.Registration;
import com.LiqaaTech.Entities.User;
import com.LiqaaTech.Enums.RegistrationStatus;
import com.LiqaaTech.Repositories.EventRepository;
import com.LiqaaTech.Repositories.RegistrationRepository;
import com.LiqaaTech.Repositories.UserRepository;
import com.LiqaaTech.Services.Interf.RegistrationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<RegistrationDTO> getAllRegistrations() {
        return registrationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RegistrationDTO getRegistrationById(Long id) {
        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Registration not found with id: " + id));
        return convertToDTO(registration);
    }

    @Override
    public RegistrationDTO createRegistration(RegistrationDTO registrationDTO) {
        User user = userRepository.findById(registrationDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + registrationDTO.getUserId()));
        
        Event event = eventRepository.findById(registrationDTO.getEventId())
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + registrationDTO.getEventId()));

        Registration registration = new Registration();
        registration.setUser(user);
        registration.setEvent(event);
        registration.setStatus(registrationDTO.getStatus());

        Registration savedRegistration = registrationRepository.save(registration);
        return convertToDTO(savedRegistration);
    }

    @Override
    public RegistrationDTO updateRegistration(Long id, RegistrationDTO registrationDTO) {
        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Registration not found with id: " + id));

        if (registrationDTO.getStatus() != null) {
            registration.setStatus(registrationDTO.getStatus());
        }

        Registration updatedRegistration = registrationRepository.save(registration);
        return convertToDTO(updatedRegistration);
    }

    @Override
    public void deleteRegistration(Long id) {
        if (!registrationRepository.existsById(id)) {
            throw new EntityNotFoundException("Registration not found with id: " + id);
        }
        registrationRepository.deleteById(id);
    }

    @Override
    public List<RegistrationDTO> getRegistrationsByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        return registrationRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RegistrationDTO> getRegistrationsByEvent(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new EntityNotFoundException("Event not found with id: " + eventId);
        }
        return registrationRepository.findByEventId(eventId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RegistrationDTO cancelRegistration(Long id) {
        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Registration not found with id: " + id));
        
        registration.setStatus(RegistrationStatus.CANCELLED);
        Registration cancelledRegistration = registrationRepository.save(registration);
        return convertToDTO(cancelledRegistration);
    }

    @Override
    public List<RegistrationDTO> getRegistrationsByEventId(Long eventId) {
        return getRegistrationsByEvent(eventId);
    }

    @Override
    public List<RegistrationDTO> getRegistrationsByUserId(Long userId) {
        return getRegistrationsByUser(userId);
    }

    @Override
    public int getTotalRegistrations() {
        return (int) registrationRepository.count();
    }

    private RegistrationDTO convertToDTO(Registration registration) {
        RegistrationDTO dto = new RegistrationDTO();
        dto.setId(registration.getId());
        dto.setUserId(registration.getUser().getId());
        dto.setEventId(registration.getEvent().getId());
        dto.setRegistrationDate(registration.getRegistrationDate());
        dto.setStatus(registration.getStatus());
        return dto;
    }
}