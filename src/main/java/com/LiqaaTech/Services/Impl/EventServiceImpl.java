package com.LiqaaTech.Services.Impl;

import com.LiqaaTech.DTOs.EventDTO;
import com.LiqaaTech.Entities.Event;
import com.LiqaaTech.Entities.User;
import com.LiqaaTech.Mappers.EventMapper;
import com.LiqaaTech.Repositories.EventRepository;
import com.LiqaaTech.Services.Interf.EventService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventServiceImpl implements EventService {
    private final EventMapper eventMapper;
    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventMapper eventMapper, EventRepository eventRepository) {
        this.eventMapper = eventMapper;
        this.eventRepository = eventRepository;
    }

    @Override
    public EventDTO createEvent(EventDTO eventDTO) {
        Event event = eventMapper.toEntity(eventDTO);
        
        if (eventDTO.getOrganizerId() != null) {
            User organizer = new User();
            organizer.setId(eventDTO.getOrganizerId());
            event.setOrganizer(organizer);
        }

        Event savedEvent = eventRepository.save(event);
        return eventMapper.toDTO(savedEvent);
    }

    @Override
    public EventDTO updateEvent(Long eventId, EventDTO eventDTO) {
        Event existingEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + eventId));

        existingEvent.setTitle(eventDTO.getTitle());
        existingEvent.setLocation(eventDTO.getLocation());
        existingEvent.setDescription(eventDTO.getDescription());
        existingEvent.setStartDateTime(eventDTO.getStartDateTime());
        existingEvent.setEndDateTime(eventDTO.getEndDateTime());
        existingEvent.setImageUrl(eventDTO.getImageUrl());
        existingEvent.setIsActive(eventDTO.getIsActive());
        existingEvent.setCapacity(eventDTO.getCapacity());
        existingEvent.setPrice(eventDTO.getPrice());

        if (eventDTO.getOrganizerId() != null) {
            User organizer = new User();
            organizer.setId(eventDTO.getOrganizerId());
            existingEvent.setOrganizer(organizer);
        }

        Event updatedEvent = eventRepository.save(existingEvent);
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
        return eventRepository.findAll().stream()
                .map(eventMapper::toDTO)
                .collect(Collectors.toList());
    }
}