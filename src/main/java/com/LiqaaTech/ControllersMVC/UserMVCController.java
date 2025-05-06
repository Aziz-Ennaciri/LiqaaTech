package com.LiqaaTech.ControllersMVC;

import com.LiqaaTech.Services.Interf.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserMVCController {

    private final UserService userService;

    @Autowired
    public UserMVCController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUsersPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/list";
    }

    @GetMapping("/{id}")
    public String showUserDetails(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "users/details";
    }

    @GetMapping("/profile")
    public String showUserProfile(Model model) {
        // TODO: Get current user from security context
        return "users/profile";
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "users/edit";
    }
} 