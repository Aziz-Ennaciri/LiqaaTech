package com.LiqaaTech.Mappers;

import com.LiqaaTech.DTOs.EventDTO;
import com.LiqaaTech.DTOs.RegistrationDTO;
import com.LiqaaTech.DTOs.CategoryDTO;
import com.LiqaaTech.Entities.Event;
import com.LiqaaTech.Entities.Registration;
import com.LiqaaTech.Entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EventMapper {
    @Autowired
    private RegistrationMapper registrationMapper;
    
    @Autowired
    private CategoryMapper categoryMapper;

    public EventDTO toDTO(Event event) {
        if (event == null) {
            return null;
        }

        return EventDTO.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .startDateTime(event.getStartDateTime())
                .endDateTime(event.getEndDateTime())
                .location(event.getLocation())
                .imageUrl(event.getImageUrl())
                .capacity(event.getCapacity())
                .price(event.getPrice())
                .isActive(event.getIsActive())
                .organizerId(event.getOrganizer().getId())
                .organizerName(event.getOrganizer().getUsername())
                .categories(convertCategoriesToDTO(event.getCategories()))
                .registrations(convertRegistrationsToDTO(event.getRegistrations()))
                .build();
    }

    public Event toEntity(EventDTO eventDTO) {
        if (eventDTO == null) {
            return null;
        }

        Event event = new Event();
        event.setId(eventDTO.getId());
        event.setTitle(eventDTO.getTitle());
        event.setDescription(eventDTO.getDescription());
        event.setStartDateTime(eventDTO.getStartDateTime());
        event.setEndDateTime(eventDTO.getEndDateTime());
        event.setLocation(eventDTO.getLocation());
        event.setImageUrl(eventDTO.getImageUrl());
        event.setCapacity(eventDTO.getCapacity());
        event.setPrice(eventDTO.getPrice());
        event.setIsActive(eventDTO.getIsActive());
        return event;
    }

    private List<CategoryDTO> convertCategoriesToDTO(Set<Category> categories) {
        if (categories == null) {
            return null;
        }
        return categories.stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    private List<RegistrationDTO> convertRegistrationsToDTO(Set<Registration> registrations) {
        if (registrations == null) {
            return null;
        }
        return registrations.stream()
                .map(registrationMapper::toDTO)
                .collect(Collectors.toList());
    }
}