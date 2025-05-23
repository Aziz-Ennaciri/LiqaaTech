package com.LiqaaTech.ControllersMVC;

import com.LiqaaTech.DTOs.EventDTO;
import com.LiqaaTech.DTOs.EventCreateDTO;
import com.LiqaaTech.Security.Services.UserDetailsImpl;
import com.LiqaaTech.Services.Interf.EventService;
import com.LiqaaTech.Services.Interf.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping({"/events"})
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
                             @RequestParam(required = false, defaultValue = "startDateTime") String sortField,
                             @RequestParam(required = false, defaultValue = "asc") String sortDir,
                             @RequestParam(required = false) String search,
                             @RequestParam(required = false) String category,
                             @RequestParam(required = false) String date,
                             @RequestParam(required = false) String price,
                             HttpServletRequest request) {
        model.addAttribute("search", search);
        model.addAttribute("category", category);
        model.addAttribute("date", date);
        model.addAttribute("price", price);
        model.addAttribute("categories", categoryService.getAllCategories());
        String currentUrl = request.getRequestURI();
        model.addAttribute("currentUrl", currentUrl);
        Page<EventDTO> eventsPage = eventService.getAllEvents(PageRequest.of(page, size,
                Sort.by(Sort.Direction.fromString(sortDir), sortField)));
        model.addAttribute("eventsPage", eventsPage);
        model.addAttribute("events", eventsPage.getContent());
        model.addAttribute("currentPage", eventsPage.getNumber());
        model.addAttribute("totalPages", eventsPage.getTotalPages());
        model.addAttribute("totalItems", eventsPage.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("search", search);
        model.addAttribute("category", category);
        model.addAttribute("date", date);
        model.addAttribute("price", price);
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
        model.addAttribute("eventDTO", new EventCreateDTO());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "events/create";
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String createEvent(@Valid EventCreateDTO eventDTO, BindingResult result,
                              RedirectAttributes redirectAttributes,
                              @AuthenticationPrincipal UserDetailsImpl currentUser,
                              HttpServletRequest request) {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                redirectAttributes.addFlashAttribute("error", error.getDefaultMessage());
            }
            redirectAttributes.addFlashAttribute("eventDTO", eventDTO);
            redirectAttributes.addFlashAttribute("categories", categoryService.getAllCategories());
            return "redirect:/events/create";
        }

        try {
            eventService.createEvent(eventDTO, currentUser);
            redirectAttributes.addFlashAttribute("success", "Event created successfully!");
            return "redirect:/events";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to create event: " + e.getMessage());
            redirectAttributes.addFlashAttribute("eventDTO", eventDTO);
            return "redirect:/events/create";
        }
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String showEditEventForm(@PathVariable Long id, Model model) {
        EventDTO event = eventService.getEventById(id);
        model.addAttribute("event", event);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "events/edit";
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateEvent(@PathVariable Long id, @Valid EventDTO eventDTO,
                              BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.event", result);
            redirectAttributes.addFlashAttribute("eventDTO", eventDTO);
            return "redirect:/events/edit/" + id;
        }
        eventService.updateEvent(id, eventDTO);
        return "redirect:/events";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return "redirect:/events";
    }
}