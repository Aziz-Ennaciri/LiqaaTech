package com.LiqaaTech.ControllersMVC;

import com.LiqaaTech.Services.Interf.EventService;
import com.LiqaaTech.Services.Interf.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EventsController {

    private final EventService eventService;
    private final CategoryService categoryService;

    @Autowired
    public EventsController(EventService eventService, CategoryService categoryService) {
        this.eventService = eventService;
        this.categoryService = categoryService;
    }

    @GetMapping("/all-events")
    public String showEvents(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String categoryId,
            Model model) {
        
        // Get all events with optional filtering
        var events = eventService.getAllEvents();
        
        // Filter by category ID if provided
        if (categoryId != null && !categoryId.isEmpty()) {
            try {
                Long catId = Long.parseLong(categoryId);
                events = events.stream()
                        .filter(e -> e.getCategory() != null && e.getCategory().equals(catId))
                        .toList();
            } catch (NumberFormatException e) {
                // Invalid category ID, ignore the filter
            }
        }
        
        // Filter by search term if provided
        if (search != null && !search.isEmpty()) {
            events = events.stream()
                    .filter(e -> e.getTitle().toLowerCase().contains(search.toLowerCase()))
                    .toList();
        }

        model.addAttribute("events", events);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("search", search);
        model.addAttribute("selectedCategory", categoryId);
        
        return "events/list";
    }
}
