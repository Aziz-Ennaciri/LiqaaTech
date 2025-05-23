package com.LiqaaTech.ControllersMVC;

import com.LiqaaTech.Security.Services.UserDetailsImpl;
import com.LiqaaTech.Services.Interf.EventService;
import com.LiqaaTech.Services.Interf.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final EventService eventService;
    private final UserService userService;

    public DashboardController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public String showDashboard(@AuthenticationPrincipal UserDetailsImpl currentUser, Model model) {
        var upcomingEvents = eventService.getUpcomingEvents(currentUser.getId());
        var userRegistrations = eventService.getUserRegistrations(currentUser.getId());
        var createdEvents = eventService.getCreatedEvents(currentUser.getId());
        model.addAttribute("upcomingEvents", upcomingEvents);
        model.addAttribute("registrations", userRegistrations);
        model.addAttribute("createdEvents", createdEvents);
        return "user-dashboard";
    }
}
