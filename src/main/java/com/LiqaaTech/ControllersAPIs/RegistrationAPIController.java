package com.LiqaaTech.ControllersAPIs;

import com.LiqaaTech.DTOs.RegistrationDTO;
import com.LiqaaTech.Services.Interf.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registrations")
@Tag(name = "Registration API", description = "APIs for managing event registrations")
public class RegistrationAPIController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationAPIController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    @Operation(summary = "Get all registrations", description = "Retrieves a list of all registrations")
    public ResponseEntity<List<RegistrationDTO>> getAllRegistrations() {
        return ResponseEntity.ok(registrationService.getAllRegistrations());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get registration by ID", description = "Retrieves a specific registration by its ID")
    public ResponseEntity<RegistrationDTO> getRegistrationById(@PathVariable Long id) {
        return ResponseEntity.ok(registrationService.getRegistrationById(id));
    }

    @PostMapping
    @Operation(summary = "Create new registration", description = "Creates a new registration for an event")
    public ResponseEntity<RegistrationDTO> createRegistration(@RequestBody RegistrationDTO registrationDTO) {
        return ResponseEntity.ok(registrationService.createRegistration(registrationDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update registration", description = "Updates an existing registration")
    public ResponseEntity<RegistrationDTO> updateRegistration(@PathVariable Long id, @RequestBody RegistrationDTO registrationDTO) {
        return ResponseEntity.ok(registrationService.updateRegistration(id, registrationDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete registration", description = "Deletes a registration by its ID")
    public ResponseEntity<Void> deleteRegistration(@PathVariable Long id) {
        registrationService.deleteRegistration(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/event/{eventId}")
    @Operation(summary = "Get registrations by event", description = "Retrieves all registrations for a specific event")
    public ResponseEntity<List<RegistrationDTO>> getRegistrationsByEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(registrationService.getRegistrationsByEventId(eventId));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get registrations by user", description = "Retrieves all registrations for a specific user")
    public ResponseEntity<List<RegistrationDTO>> getRegistrationsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(registrationService.getRegistrationsByUserId(userId));
    }
} 