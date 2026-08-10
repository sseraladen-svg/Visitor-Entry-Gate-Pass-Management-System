package com.visiongate;

import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password) {

        if (username.equals("admin") && password.equals("admin123")) {
            return "Login successful";
        }

        return "Invalid username or password";
    }
}