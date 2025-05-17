package com.LiqaaTech.Services.Interf;

import com.LiqaaTech.Entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface EventService {
    List<Event> getAllEvents();
    Page<Event> getAllEvents(Pageable pageable);
    Event getEventById(Long id);
    Event createEvent(Event event);
    Event updateEvent(Long id, Event event);
    void deleteEvent(Long id);
    Page<Event> findAllEvents(Pageable pageable);
    Page<Event> getEventsByCategory(Long categoryId, Pageable pageable);
    List<Event> getUpcomingEvents();
    Event save(Event event);
}
