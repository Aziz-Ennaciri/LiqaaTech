package com.LiqaaTech.ControllersMVC;

import com.LiqaaTech.Security.Services.UserDetailsImpl;
import com.LiqaaTech.Services.Interf.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public String listTickets(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            model.addAttribute("tickets", ticketService.getTicketsByUserId(userDetails.getId()));
        } else {
            model.addAttribute("tickets", ticketService.getAllTickets());
        }
        return "tickets/list";
    }

    @GetMapping("/event/{eventId}")
    public String listEventTickets(@PathVariable Long eventId, Model model) {
        model.addAttribute("tickets", ticketService.getTicketsByEventId(eventId));
        model.addAttribute("eventId", eventId);
        return "tickets/event-list";
    }

    @GetMapping("/user/{userId}")
    public String listUserTickets(@PathVariable Long userId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            if (userDetails.getId().equals(userId)) {
                model.addAttribute("tickets", ticketService.getTicketsByUserId(userId));
                return "tickets/user-list";
            }
        }
        return "redirect:/tickets";
    }
} 