package com.LiqaaTech.Controllers;

import com.LiqaaTech.Enums.UserRole;
import com.LiqaaTech.Services.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserServiceImpl userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/assign-role")
    public String assignRole(@RequestParam String email, @RequestParam UserRole role) {
        userService.assignRoleToUser(email, role);
        return "Role assigned successfully!";
    }
} 