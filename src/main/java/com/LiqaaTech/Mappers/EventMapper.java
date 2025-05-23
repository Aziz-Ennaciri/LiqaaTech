package com.LiqaaTech.Mappers;

import com.LiqaaTech.DTOs.EventDTO;
import com.LiqaaTech.DTOs.EventCreateDTO;
import com.LiqaaTech.Entities.Event;
import com.LiqaaTech.Entities.Category;
import com.LiqaaTech.Entities.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventMapper {

    public Event toEntityFromCreateDTO(EventCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        Event event = new Event();
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setStartDateTime(dto.getStartDateTime());
        event.setLocation(dto.getLocation());
        event.setCapacity(dto.getCapacity());
        event.setPrice(dto.getPrice());
        
        if (dto.getCategory() != null) {
            event.setCategory(new Category()); // This will be set by EventServiceImpl
        }
        if (dto.getOrganizer() != null) {
            event.setOrganizer(new User()); // This will be set by EventServiceImpl
        }
        return event;
    }

    public Event toEntityFromDTO(EventDTO dto) {
        if (dto == null) {
            return null;
        }
        Event event = new Event();
        event.setId(dto.getId());
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setStartDateTime(dto.getStartDateTime());
        event.setLocation(dto.getLocation());
        event.setCapacity(dto.getMaxTickets());
        event.setPrice(dto.getPrice());
        if (dto.getCategory() != null) {
            event.setCategory(new Category()); // Will be set by EventServiceImpl
        }
        return event;
    }

    public EventDTO toDTO(Event event) {
        if (event == null) {
            return null;
        }
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
        dto.setAvailableSpots(event.getAvailableSpots());
        return dto;
    }

    public Event toEntity(EventDTO dto) {
        if (dto == null) {
            return null;
        }
        Event event = new Event();
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setStartDateTime(dto.getStartDateTime());
        event.setLocation(dto.getLocation());
        event.setCapacity(dto.getCapacity());
        event.setPrice(dto.getPrice());
        return event;
    }

    public void updateEntityFromDTO(EventDTO dto, Event event) {
        if (dto == null || event == null) {
            return;
        }
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setStartDateTime(dto.getStartDateTime());
        event.setLocation(dto.getLocation());
        event.setCapacity(dto.getCapacity());
        event.setPrice(dto.getPrice());
    }

    public List<EventDTO> toDTOList(List<Event> events) {
        if (events == null) {
            return null;
        }
        return events.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Event> toEntityList(List<EventDTO> dtos) {
        if (dtos == null) {
            return null;
        }
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}