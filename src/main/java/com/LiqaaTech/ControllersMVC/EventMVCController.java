package com.LiqaaTech.ControllersMVC;

import com.LiqaaTech.Entities.Event;
import com.LiqaaTech.Entities.User;
import com.LiqaaTech.Services.Interf.EventService;
import com.LiqaaTech.Services.Interf.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.access.prepost.PreAuthorize;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Controller
@RequestMapping("/events")
public class EventMVCController {

    private final EventService eventService;
    private final CategoryService categoryService;

    @Autowired
    public EventMVCController(EventService eventService, CategoryService categoryService) {
        this.eventService = eventService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String showEvents(Model model, @RequestParam(required = false, defaultValue = "0") int page,
                            @RequestParam(required = false, defaultValue = "10") int size,
                            @RequestParam(required = false, defaultValue = "dateTime") String sortField,
                            @RequestParam(required = false, defaultValue = "asc") String sortDir) {
        Page<Event> eventsPage = eventService.findAllEvents(PageRequest.of(page, size,
                Sort.by(Sort.Direction.fromString(sortDir), sortField)));
        
        // Add both the Page object and its content to the model
        model.addAttribute("eventsPage", eventsPage);
        model.addAttribute("events", eventsPage.getContent());
        
        // Add pagination info
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", eventsPage.getTotalPages());
        model.addAttribute("totalItems", eventsPage.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        return "events/list";
    }

    @GetMapping("/{id}")
    public String showEventDetails(@PathVariable Long id, Model model) {
        model.addAttribute("event", eventService.getEventById(id));
        return "events/details";
    }

    @GetMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String showCreateEventForm(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "events/create";
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String createEvent(@Valid Event event, BindingResult result,
                             RedirectAttributes redirectAttributes,
                             @RequestParam("_csrf") String csrfToken) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.event", result);
            redirectAttributes.addFlashAttribute("event", event);
            return "redirect:/events/create";
        }
        
        try {
            // Set the current user as organizer
            event.setOrganizer(new User());
            event.getOrganizer().setId(1L); // This should be set from security context
            
            // Set category if not already set
            if (event.getCategory() == null || event.getCategory().getId() == null) {
                throw new IllegalArgumentException("Category is required");
            }
            
            // Validate date
            if (event.getDateTime() != null && event.getDateTime().isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException("Event date must be in the future");
            }
            
            // Validate capacity
            if (event.getCapacity() == null || event.getCapacity() <= 0) {
                throw new IllegalArgumentException("Capacity must be greater than 0");
            }
            
            // Validate price
            if (event.getPrice() == null || event.getPrice().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Price must be greater than or equal to 0");
            }
            
            eventService.createEvent(event);
            redirectAttributes.addFlashAttribute("success", "Event created successfully!");
            return "redirect:/events";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("event", event);
            return "redirect:/events/create";
        }
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String showEditEventForm(@PathVariable Long id, Model model) {
        Event event = eventService.getEventById(id);
        model.addAttribute("event", event);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "events/edit";
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateEvent(@PathVariable Long id, @Valid Event event,
                             BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.event", result);
            redirectAttributes.addFlashAttribute("event", event);
            return "redirect:/events/edit/" + id;
        }
        eventService.updateEvent(id, event);
        return "redirect:/events";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return "redirect:/events";
    }
}