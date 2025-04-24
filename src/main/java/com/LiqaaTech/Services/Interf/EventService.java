package com.LiqaaTech.Services.Interf;

import com.LiqaaTech.DTOs.EventDTO;

import java.util.List;

public interface EventService {
    EventDTO createEvent(EventDTO eventDTO);
    EventDTO updateEvent(Long eventId, EventDTO eventDTO);
    void deleteEvent(Long eventId);
    EventDTO getEventById(Long eventId);
    List<EventDTO> getAllEvents();
}
