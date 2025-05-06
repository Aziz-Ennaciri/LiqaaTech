package com.LiqaaTech.ControllersMVC;

import com.LiqaaTech.Services.Interf.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/events")
public class EventMVCController {

    private final EventService eventService;

    @Autowired
    public EventMVCController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public String showEventsPage(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        return "events/list";
    }

    @GetMapping("/{id}")
    public String showEventDetails(@PathVariable Long id, Model model) {
        model.addAttribute("event", eventService.getEventById(id));
        return "events/details";
    }

    @GetMapping("/create")
    public String showCreateEventForm(Model model) {
        return "events/create";
    }

    @GetMapping("/edit/{id}")
    public String showEditEventForm(@PathVariable Long id, Model model) {
        model.addAttribute("event", eventService.getEventById(id));
        return "events/edit";
    }
} 