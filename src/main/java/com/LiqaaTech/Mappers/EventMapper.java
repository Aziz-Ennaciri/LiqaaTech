package com.LiqaaTech.Mappers;

import com.LiqaaTech.DTOs.EventDTO;
import com.LiqaaTech.Entities.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventMapper {
    @Autowired
    @Lazy
    private RegistrationMapper registrationMapper;
    @Autowired
    @Lazy
    private UserMapper userMapper;

    public EventDTO toDTO(Event event) {
        if (event == null) {
            return null;
        }

        EventDTO eventDTO = new EventDTO();
        eventDTO.setTitle(event.getTitle());
        eventDTO.setLocation(event.getLocation());
        eventDTO.setDate(event.getDate());
        eventDTO.setDescription(event.getDescription());
        eventDTO.setImageUrl(event.getImageUrl());
        eventDTO.setPublic(event.isPublic());
        eventDTO.setCategory(event.getCategory());

        if (event.getOrganizer() != null) {
            eventDTO.setOrganizerDTO(userMapper.toDTOWithoutEvents(event.getOrganizer()));
        }

        if (event.getRegistrations() != null) {
            eventDTO.setRegistrationsDTO(registrationMapper.toDTOListWithoutEvent(event.getRegistrations()));
        }

        return eventDTO;
    }

    public Event toEntity(EventDTO eventDTO) {
        if (eventDTO == null) {
            return null;
        }

        Event event = new Event();
        event.setTitle(eventDTO.getTitle());
        event.setLocation(eventDTO.getLocation());
        event.setDate(eventDTO.getDate());
        event.setDescription(eventDTO.getDescription());
        event.setImageUrl(eventDTO.getImageUrl());
        event.setPublic(eventDTO.isPublic());
        event.setCategory(eventDTO.getCategory());

        if (eventDTO.getOrganizerDTO() != null) {
            event.setOrganizer(userMapper.toEntityWithoutEvents(eventDTO.getOrganizerDTO()));
        }

        if (eventDTO.getRegistrationsDTO() != null) {
            event.setRegistrations(registrationMapper.toEntityListWithoutEvent(eventDTO.getRegistrationsDTO()));
        }

        return event;
    }

    public List<EventDTO> toDTOList(List<Event> events) {
        if (events == null) {
            return new ArrayList<>();
        }
        return events.stream().map(this::toDTO).toList();
    }

    public List<Event> toEntityList(List<EventDTO> dtos) {
        if (dtos == null) {
            return new ArrayList<>();
        }
        return dtos.stream().map(this::toEntity).toList();
    }

    public EventDTO toDTOWithoutRegistrations(Event event) {
        if (event == null) {
            return null;
        }

        EventDTO eventDTO = new EventDTO();
        eventDTO.setTitle(event.getTitle());
        eventDTO.setLocation(event.getLocation());
        eventDTO.setDate(event.getDate());
        eventDTO.setDescription(event.getDescription());
        eventDTO.setImageUrl(event.getImageUrl());
        eventDTO.setPublic(event.isPublic());
        eventDTO.setCategory(event.getCategory());

        if (event.getOrganizer() != null) {
            eventDTO.setOrganizerDTO(userMapper.toDTOWithoutEvents(event.getOrganizer()));
        }

        return eventDTO;
    }
}