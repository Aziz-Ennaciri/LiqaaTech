package com.LiqaaTech.ControllersAPIs;

import com.LiqaaTech.DTOs.TicketDTO;
import com.LiqaaTech.Services.Interf.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@Tag(name = "Ticket API", description = "APIs for managing event tickets")
public class TicketAPIController {

    private final TicketService ticketService;

    @Autowired
    public TicketAPIController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    @Operation(summary = "Get all tickets", description = "Retrieves a list of all tickets")
    public ResponseEntity<List<TicketDTO>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get ticket by ID", description = "Retrieves a specific ticket by its ID")
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    @PostMapping
    @Operation(summary = "Create new ticket", description = "Creates a new ticket for an event")
    public ResponseEntity<TicketDTO> createTicket(@RequestBody TicketDTO ticketDTO) {
        return ResponseEntity.ok(ticketService.createTicket(ticketDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update ticket", description = "Updates an existing ticket")
    public ResponseEntity<TicketDTO> updateTicket(@PathVariable Long id, @RequestBody TicketDTO ticketDTO) {
        return ResponseEntity.ok(ticketService.updateTicket(id, ticketDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete ticket", description = "Deletes a ticket by its ID")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/event/{eventId}")
    @Operation(summary = "Get tickets by event", description = "Retrieves all tickets for a specific event")
    public ResponseEntity<List<TicketDTO>> getTicketsByEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(ticketService.getTicketsByEventId(eventId));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get tickets by user", description = "Retrieves all tickets for a specific user")
    public ResponseEntity<List<TicketDTO>> getTicketsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(ticketService.getTicketsByUserId(userId));
    }
} 