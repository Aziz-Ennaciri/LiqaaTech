package com.LiqaaTech.Services.Impl;

import com.LiqaaTech.DTOs.EventDTO;
import com.LiqaaTech.Services.Interf.EventService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    @Override
    public EventDTO createEvent(EventDTO eventDTO) {
        return null;
    }

    @Override
    public EventDTO updateEvent(Long eventId, EventDTO eventDTO) {
        return null;
    }

    @Override
    public void deleteEvent(Long eventId) {

    }

    @Override
    public EventDTO getEventById(Long eventId) {
        return null;
    }

    @Override
    public List<EventDTO> getAllEvents() {
        return null;
    }
}
