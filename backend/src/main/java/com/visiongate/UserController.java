package com.visiongate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password) {

        System.out.println("Login attempt - Username: " + username + ", Password: " + password);

        if (username.equals("admin") && password.equals("admin123")) {
            System.out.println("Login successful");
            return "redirect:/index.html";
        }

        System.out.println("Login failed");
        return "redirect:/login.html?error=true";
    }
}