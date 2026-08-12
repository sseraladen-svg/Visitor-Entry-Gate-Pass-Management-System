package com.visiongate;

import com.visiongate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password) {

        System.out.println("Login attempt - Username: " + username);

        if (userService.validateCredentials(username, password)) {
            System.out.println("Login successful for user: " + username);
            return "redirect:/index.html";
        }

        System.out.println("Login failed for user: " + username);
        return "redirect:/login.html?error=true";
    }
}