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
@Tag(name = "Registration Management", description = "APIs for managing event registrations")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

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
    public ResponseEntity<RegistrationDTO> updateRegistration(
            @PathVariable Long id,
            @RequestBody RegistrationDTO registrationDTO) {
        return ResponseEntity.ok(registrationService.updateRegistration(id, registrationDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete registration", description = "Deletes a registration by its ID")
    public ResponseEntity<Void> deleteRegistration(@PathVariable Long id) {
        registrationService.deleteRegistration(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user's registrations", description = "Retrieves all registrations for a specific user")
    public ResponseEntity<List<RegistrationDTO>> getRegistrationsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(registrationService.getRegistrationsByUser(userId));
    }

    @GetMapping("/event/{eventId}")
    @Operation(summary = "Get event's registrations", description = "Retrieves all registrations for a specific event")
    public ResponseEntity<List<RegistrationDTO>> getRegistrationsByEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(registrationService.getRegistrationsByEvent(eventId));
    }

    @PatchMapping("/{id}/cancel")
    @Operation(summary = "Cancel registration", description = "Cancels an existing registration")
    public ResponseEntity<RegistrationDTO> cancelRegistration(@PathVariable Long id) {
        return ResponseEntity.ok(registrationService.cancelRegistration(id));
    }
} 