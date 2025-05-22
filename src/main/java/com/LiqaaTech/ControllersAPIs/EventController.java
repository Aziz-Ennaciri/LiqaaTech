package com.LiqaaTech.ControllersAPIs;

import com.LiqaaTech.DTOs.EventDTO;
import com.LiqaaTech.DTOs.EventCreateDTO;
import com.LiqaaTech.Security.Services.UserDetailsImpl;
import com.LiqaaTech.Services.Interf.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@Tag(name = "Event Management", description = "APIs for managing events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    @Operation(summary = "Get all events")
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get event by ID")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @PostMapping
    @Operation(summary = "Create new event")
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventCreateDTO eventDTO,
                                               @AuthenticationPrincipal UserDetailsImpl currentUser) {
        return ResponseEntity.ok(eventService.createEvent(eventDTO, currentUser));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update event")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @RequestBody EventDTO eventDTO) {
        return ResponseEntity.ok(eventService.updateEvent(id, eventDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete event")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok().build();
    }
}