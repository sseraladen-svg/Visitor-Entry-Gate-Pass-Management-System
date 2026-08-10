package com.visiongate;

import com.visiongate.entity.UserEntity;
import com.visiongate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password) {

        System.out.println("Login attempt - Username: " + username);

        UserEntity user = userRepository.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Login successful for user: " + username);
            return "redirect:/index.html";
        }

        System.out.println("Login failed for user: " + username);
        return "redirect:/login.html?error=true";
    }
}