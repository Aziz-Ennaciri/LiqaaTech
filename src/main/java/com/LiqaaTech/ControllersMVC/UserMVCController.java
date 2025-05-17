package com.LiqaaTech.ControllersMVC;

import com.LiqaaTech.Services.Interf.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserMVCController {

    private final UserService userService;

    @Autowired
    public UserMVCController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String showUserProfile(Model model) {
        return "auth/profile";
    }

    @GetMapping("/settings")
    public String showSettingsPage(Model model) {
        return "auth/settings";
    }
} 