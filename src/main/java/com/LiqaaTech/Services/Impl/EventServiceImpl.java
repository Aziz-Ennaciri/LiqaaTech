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

import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RegistrationMapper registrationMapper;

    @Override
    public EventDTO createEvent(EventDTO eventDTO) {
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
            List<Registration> registrations = registrationMapper.toEntityList(eventDTO.getRegistrationsDTO());
            registrations.forEach(r -> r.setEvent(savedEvent));
            savedEvent.setRegistrations(registrations);
        }
        Event updatedEvent = eventRepository.save(savedEvent);
        return eventMapper.toDTO(updatedEvent);
    }

    @Override
    public void deleteEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + eventId));
        eventRepository.delete(event);
    }

    @Override
    public EventDTO getEventById(Long eventId) {
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
