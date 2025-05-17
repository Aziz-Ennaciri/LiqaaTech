package com.LiqaaTech.Services.Impl;

import com.LiqaaTech.Entities.Event;
import com.LiqaaTech.Exceptions.NotFoundException;
import com.LiqaaTech.Repositories.CategoryRepository;
import com.LiqaaTech.Repositories.EventRepository;
import com.LiqaaTech.Repositories.UserRepository;
import com.LiqaaTech.Services.Interf.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Autowired
    public EventServiceImpl(
            EventRepository eventRepository,
            CategoryRepository categoryRepository,
            UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Page<Event> getAllEvents(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    @Override
    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event not found with id: " + id));
    }

    @Override
    public Event createEvent(Event event) {
        // Validate required fields
        if (event.getTitle() == null || event.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Event title is required");
        }
        if (event.getDateTime() == null) {
            throw new IllegalArgumentException("Event date and time are required");
        }
        if (event.getCapacity() == null || event.getCapacity() <= 0) {
            throw new IllegalArgumentException("Event capacity must be greater than 0");
        }
        if (event.getCategory() == null || event.getCategory().getId() == null) {
            throw new IllegalArgumentException("Event category is required");
        }
        if (event.getOrganizer() == null || event.getOrganizer().getId() == null) {
            throw new IllegalArgumentException("Event organizer is required");
        }
        
        // Validate event date
        if (event.getDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Event date must be in the future");
        }
        
        // Set default values
        event.setDeleted(false);
        
        // Set category and organizer references
        event.setCategory(categoryRepository.findById(event.getCategory().getId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found")));
        event.setOrganizer(userRepository.findById(event.getOrganizer().getId())
                .orElseThrow(() -> new IllegalArgumentException("Organizer not found")));
        
        // Save event
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(Long id, Event event) {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event not found with id: " + id));

        // Update all fields except ID and deleted status
        existingEvent.setTitle(event.getTitle());
        existingEvent.setDescription(event.getDescription());
        existingEvent.setDateTime(event.getDateTime());
        existingEvent.setCategory(event.getCategory());
        existingEvent.setOrganizer(event.getOrganizer());
        
        return eventRepository.save(existingEvent);
    }

    @Override
    public Page<Event> findAllEvents(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    @Override
    public Page<Event> getEventsByCategory(Long categoryId, Pageable pageable) {
        return eventRepository.findByCategoryId(categoryId, pageable);
    }

    @Override
    public List<Event> getUpcomingEvents() {
        return eventRepository.findUpcomingEvents(LocalDateTime.now());
    }

    @Override
    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new NotFoundException("Event not found with id: " + id);
        }
        eventRepository.deleteById(id);
    }
    public Event save(Event event) {
        return eventRepository.save(event);
    }
}