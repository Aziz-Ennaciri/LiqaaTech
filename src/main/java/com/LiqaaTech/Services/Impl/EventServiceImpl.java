package com.LiqaaTech.Services.Impl;

import com.LiqaaTech.DTOs.EventDTO;
import com.LiqaaTech.Entities.Event;
import com.LiqaaTech.Entities.Registration;
import com.LiqaaTech.Entities.User;
import com.LiqaaTech.Exceptions.NotFoundException;
import com.LiqaaTech.Mappers.EventMapper;
import com.LiqaaTech.Mappers.RegistrationMapper;
import com.LiqaaTech.Mappers.UserMapper;
import com.LiqaaTech.Repositories.EventRepository;
import com.LiqaaTech.Services.Interf.EventService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EventServiceImpl implements EventService {
    private final EventMapper eventMapper;
    private final EventRepository eventRepository;
    private final UserMapper userMapper;
    private final RegistrationMapper registrationMapper;

    @Autowired
    public EventServiceImpl(EventMapper eventMapper,
                            EventRepository eventRepository,
                            UserMapper userMapper,
                            RegistrationMapper registrationMapper) {
        this.eventMapper = eventMapper;
        this.eventRepository = eventRepository;
        this.userMapper = userMapper;
        this.registrationMapper = registrationMapper;
    }

    @Override
    public EventDTO createEvent(EventDTO eventDTO) {
        if (eventDTO == null) {
            throw new IllegalArgumentException("Event data cannot be null");
        }

        Event event = eventMapper.toEntity(eventDTO);

        if (eventDTO.getOrganizerDTO() != null) {
            User organizer = userMapper.toEntity(eventDTO.getOrganizerDTO());
            event.setOrganizer(organizer);
        }

        if (eventDTO.getRegistrationsDTO() != null) {
            List<Registration> registrations = registrationMapper.toEntityList(eventDTO.getRegistrationsDTO());
            registrations.forEach(r -> r.setEvent(event));
            event.setRegistrations(registrations);
        }

        Event savedEvent = eventRepository.save(event);
        return eventMapper.toDTO(savedEvent);
    }

    @Override
    public EventDTO updateEvent(Long eventId, EventDTO eventDTO) {
        if (eventId == null || eventDTO == null) {
            throw new IllegalArgumentException("Event ID and data cannot be null");
        }

        Event savedEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + eventId));

        savedEvent.setTitle(eventDTO.getTitle());
        savedEvent.setLocation(eventDTO.getLocation());
        savedEvent.setDescription(eventDTO.getDescription());
        savedEvent.setDate(eventDTO.getDate());
        savedEvent.setImageUrl(eventDTO.getImageUrl());
        savedEvent.setPublic(eventDTO.isPublic());
        savedEvent.setCategory(eventDTO.getCategory());

        if (eventDTO.getOrganizerDTO() != null) {
            User organizer = userMapper.toEntity(eventDTO.getOrganizerDTO());
            savedEvent.setOrganizer(organizer);
        }

        if (eventDTO.getRegistrationsDTO() != null) {
            savedEvent.getRegistrations().clear();
            List<Registration> registrations = registrationMapper.toEntityList(eventDTO.getRegistrationsDTO());
            registrations.forEach(r -> {
                r.setEvent(savedEvent);
                savedEvent.getRegistrations().add(r);
            });
        }

        Event updatedEvent = eventRepository.save(savedEvent);
        return eventMapper.toDTO(updatedEvent);
    }

    @Override
    public void deleteEvent(Long eventId) {
        if (eventId == null) {
            throw new IllegalArgumentException("Event ID cannot be null");
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + eventId));
        eventRepository.delete(event);
    }

    @Override
    public EventDTO getEventById(Long eventId) {
        if (eventId == null) {
            throw new IllegalArgumentException("Event ID cannot be null");
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + eventId));
        return eventMapper.toDTO(event);
    }

    @Override
    public List<EventDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        if(events.isEmpty()){
            throw new NotFoundException("No Events found");
        }
        return eventMapper.toDTOList(events);
    }
}