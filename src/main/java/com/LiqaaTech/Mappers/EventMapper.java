package com.LiqaaTech.Mappers;

import com.LiqaaTech.DTOs.EventDTO;
import com.LiqaaTech.Entities.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventMapper {
    @Autowired
    private RegistrationMapper RegistrationMapper;


    public EventDTO toDTO(Event event) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setTitle(event.getTitle());
        eventDTO.setLocation(event.getLocation());
        eventDTO.setDate(event.getDate());
        eventDTO.setDescription(event.getDescription());
        eventDTO.setImageUrl(event.getImageUrl());
        eventDTO.setPublic(event.isPublic());
        eventDTO.setCategory(event.getCategory());
        eventDTO.setRegistrationsDTO(RegistrationMapper.toDTOList(event.getRegistrations()));
        return eventDTO;
    }
    public Event toEntity(EventDTO eventDTO) {
        Event event = new Event();
        event.setTitle(eventDTO.getTitle());
        event.setLocation(eventDTO.getLocation());
        event.setDate(eventDTO.getDate());
        event.setDescription(eventDTO.getDescription());
        event.setImageUrl(eventDTO.getImageUrl());
        event.setPublic(eventDTO.isPublic());
        event.setCategory(eventDTO.getCategory());
        event.setRegistrations(RegistrationMapper.toEntityList(eventDTO.getRegistrationsDTO()));
        return event;
    }

    public List<EventDTO> toDTOList(List<Event> events) {
        return events.stream().map(this::toDTO).toList();
    }

    public List<Event> toEntityList(List<EventDTO> dtos) {
        return dtos.stream().map(this::toEntity).toList();
    }

}
