package com.LiqaaTech.Services.Interf;

import com.LiqaaTech.DTOs.EventDTO;
import com.LiqaaTech.DTOs.EventCreateDTO;
import com.LiqaaTech.Security.Services.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface EventService {
    List<EventDTO> getAllEvents();
    Page<EventDTO> getAllEvents(Pageable pageable);
    EventDTO getEventById(Long id);
    EventDTO createEvent(EventCreateDTO eventDTO, UserDetailsImpl currentUser);
    EventDTO updateEvent(Long id, EventDTO eventDTO);
    void deleteEvent(Long id);
    Page<EventDTO> findAllEvents(Pageable pageable);
    Page<EventDTO> getEventsByCategory(Long categoryId, Pageable pageable);
    List<EventDTO> getUpcomingEvents();
    List<EventDTO> getUpcomingEvents(Long userId);
    List<EventDTO> getUserRegistrations(Long userId);
    List<EventDTO> getCreatedEvents(Long userId);
    EventDTO save(EventDTO eventDTO);
}