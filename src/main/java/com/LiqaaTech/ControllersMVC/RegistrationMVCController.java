package com.LiqaaTech.ControllersMVC;

import com.LiqaaTech.Services.Interf.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registrations")
public class RegistrationMVCController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationMVCController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public String showRegistrationsPage(Model model) {
        model.addAttribute("registrations", registrationService.getAllRegistrations());
        return "registrations/list";
    }

    @GetMapping("/{id}")
    public String showRegistrationDetails(@PathVariable Long id, Model model) {
        model.addAttribute("registration", registrationService.getRegistrationById(id));
        return "registrations/details";
    }

    @GetMapping("/event/{eventId}")
    public String showEventRegistrations(@PathVariable Long eventId, Model model) {
        model.addAttribute("registrations", registrationService.getRegistrationsByEventId(eventId));
        return "registrations/event-list";
    }

    @GetMapping("/user/{userId}")
    public String showUserRegistrations(@PathVariable Long userId, Model model) {
        model.addAttribute("registrations", registrationService.getRegistrationsByUserId(userId));
        return "registrations/user-list";
    }
} 