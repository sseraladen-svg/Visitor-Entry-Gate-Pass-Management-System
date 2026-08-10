package com.college.visitorgatepass.controller;

import com.college.visitorgatepass.dto.AuthDtos.UserResponse;
import com.college.visitorgatepass.service.UserService;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> all() {
        return userService.findAll().stream().map(UserResponse::from).toList();
    }

    @GetMapping("/hosts")
    public List<UserResponse> hosts() {
        return userService.findHosts().stream().map(UserResponse::from).toList();
    }
}
