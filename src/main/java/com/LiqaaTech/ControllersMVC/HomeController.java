package com.LiqaaTech.ControllersMVC;

import com.LiqaaTech.Services.Interf.CategoryService;
import com.LiqaaTech.Services.Interf.EventService;
import com.LiqaaTech.Services.Interf.UserService;
import com.LiqaaTech.Services.Interf.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final EventService eventService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final RegistrationService registrationService;

    @Autowired
    public HomeController(EventService eventService, CategoryService categoryService,
                         UserService userService, RegistrationService registrationService) {
        this.eventService = eventService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.registrationService = registrationService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("featuredEvents", eventService.getUpcomingEvents());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("totalEvents", eventService.getAllEvents().size());
        model.addAttribute("activeUsers", userService.getActiveUsersCount());
        model.addAttribute("totalRegistrations", registrationService.getTotalRegistrations());
        model.addAttribute("organizerCount", userService.getOrganizerCount());
        return "index";
    }

    @GetMapping("/home")
    public String homePage() {
        return "redirect:/";
    }
} 