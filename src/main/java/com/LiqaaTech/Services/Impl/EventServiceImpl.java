package com.LiqaaTech.Services.Impl;

import com.LiqaaTech.DTOs.EventDTO;
import com.LiqaaTech.DTOs.EventCreateDTO;
import com.LiqaaTech.Entities.Event;
import com.LiqaaTech.Entities.User;

import com.LiqaaTech.Mappers.EventMapper;
import com.LiqaaTech.Repositories.CategoryRepository;
import com.LiqaaTech.Repositories.EventRepository;
import com.LiqaaTech.Repositories.UserRepository;
import com.LiqaaTech.Repositories.RegistrationRepository;
import com.LiqaaTech.Repositories.TicketRepository;
import com.LiqaaTech.Security.Services.UserDetailsImpl;
import com.LiqaaTech.Services.Interf.EventService;
import com.LiqaaTech.Exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final RegistrationRepository registrationRepository;
    private final TicketRepository ticketRepository;
    private final EventMapper eventMapper;

    @Autowired
    public EventServiceImpl(
            EventRepository eventRepository,
            CategoryRepository categoryRepository,
            UserRepository userRepository,
            RegistrationRepository registrationRepository,
            TicketRepository ticketRepository,
            EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.registrationRepository = registrationRepository;
        this.ticketRepository = ticketRepository;
        this.eventMapper = eventMapper;
    }

    @Override
    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<EventDTO> getAllEvents(Pageable pageable) {
        return eventRepository.findAll(pageable)
                .map(this::toDTO);
    }

    @Override
    public EventDTO getEventById(Long id) {
        return toDTO(eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event not found with id: " + id)));
    }

    @Override
    public EventDTO createEvent(EventCreateDTO eventDTO, UserDetailsImpl currentUser) {
        if (eventDTO.getTitle() == null || eventDTO.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Event title is required");
        }
        if (eventDTO.getStartDateTime() == null) {
            throw new IllegalArgumentException("Event start date and time are required");
        }
        if (eventDTO.getCapacity() == null || eventDTO.getCapacity() <= 0) {
            throw new IllegalArgumentException("Event capacity must be greater than 0");
        }
        if (eventDTO.getCategory() == null) {
            throw new IllegalArgumentException("Event category is required");
        }

        Event event = new Event();
        event.setTitle(eventDTO.getTitle());
        event.setDescription(eventDTO.getDescription());
        event.setStartDateTime(eventDTO.getStartDateTime());
        event.setCapacity(eventDTO.getCapacity());
        event.setPrice(eventDTO.getPrice());
        event.setLocation(eventDTO.getLocation());

        if (currentUser == null) {
            throw new SecurityException("User not authenticated");
        }

        event.setCategory(categoryRepository.findById(eventDTO.getCategory())
                .orElseThrow(() -> new NotFoundException("Category not found")));

        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        event.setOrganizer(user);

        Event savedEvent = eventRepository.save(event);
        return eventMapper.toDTO(savedEvent);
    }

    @Override
    @Transactional
    public EventDTO updateEvent(Long id, EventDTO eventDTO) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event not found with id: " + id));
        eventMapper.updateEntityFromDTO(eventDTO, event);
        return eventMapper.toDTO(eventRepository.save(event));
    }

    @Override
    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event not found with id: " + id));
        eventRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EventDTO> findAllEvents(Pageable pageable) {
        Page<Event> events = eventRepository.findAllWithRegistrations(LocalDateTime.now(), pageable);
        return events.map(eventMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EventDTO> getEventsByCategory(Long categoryId, Pageable pageable) {
        return eventRepository.findByCategoryId(categoryId, pageable)
                .map(eventMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventDTO> getUpcomingEvents() {
        return eventRepository.findUpcomingEvents(LocalDateTime.now())
                .stream()
                .map(eventMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventDTO> getUpcomingEvents(Long userId) {
        return eventRepository.findUpcomingEventsByUser(userId, LocalDateTime.now())
                .stream()
                .map(eventMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventDTO> getUserRegistrations(Long userId) {
        return eventRepository.findEventsByRegistration(userId)
                .stream()
                .map(eventMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventDTO> getCreatedEvents(Long userId) {
        return eventRepository.findByOrganizerId(userId)
                .stream()
                .map(eventMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EventDTO save(EventDTO eventDTO) {
        Event event = eventMapper.toEntity(eventDTO);
        event.setOrganizer(userRepository.findById(eventDTO.getOrganizer())
                .orElseThrow(() -> new NotFoundException("User not found")));
        event.setCategory(categoryRepository.findById(eventDTO.getCategory())
                .orElseThrow(() -> new NotFoundException("Category not found")));
        Event savedEvent = eventRepository.save(event);
        return eventMapper.toDTO(savedEvent);
    }



    private EventDTO toDTO(Event event) {
        EventDTO dto = new EventDTO();
        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());
        dto.setStartDateTime(event.getStartDateTime());
        dto.setLocation(event.getLocation());
        dto.setCapacity(event.getCapacity());
        dto.setPrice(event.getPrice());
        if (event.getCategory() != null) {
            dto.setCategory(event.getCategory().getId());
        }
        if (event.getOrganizer() != null) {
            dto.setOrganizer(event.getOrganizer().getId());
        }
        dto.setAvailableSpots(event.getCapacity() - event.getRegistrations().size());
        return dto;
    }
}