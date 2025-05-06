package com.LiqaaTech.ControllersMVC;

import com.LiqaaTech.Services.Interf.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tickets")
public class TicketMVCController {

    private final TicketService ticketService;

    @Autowired
    public TicketMVCController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public String showTicketsPage(Model model) {
        model.addAttribute("tickets", ticketService.getAllTickets());
        return "tickets/list";
    }

    @GetMapping("/{id}")
    public String showTicketDetails(@PathVariable Long id, Model model) {
        model.addAttribute("ticket", ticketService.getTicketById(id));
        return "tickets/details";
    }

    @GetMapping("/event/{eventId}")
    public String showEventTickets(@PathVariable Long eventId, Model model) {
        model.addAttribute("tickets", ticketService.getTicketsByEventId(eventId));
        return "tickets/event-list";
    }

    @GetMapping("/user/{userId}")
    public String showUserTickets(@PathVariable Long userId, Model model) {
        model.addAttribute("tickets", ticketService.getTicketsByUserId(userId));
        return "tickets/user-list";
    }
} 