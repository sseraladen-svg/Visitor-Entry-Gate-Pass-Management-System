package com.college.visitorgatepass.controller;

import com.college.visitorgatepass.dto.AuthDtos.AuthResponse;
import com.college.visitorgatepass.dto.AuthDtos.LoginRequest;
import com.college.visitorgatepass.dto.AuthDtos.RegisterRequest;
import com.college.visitorgatepass.dto.AuthDtos.UserResponse;
import com.college.visitorgatepass.model.User;
import com.college.visitorgatepass.security.JwtService;
import com.college.visitorgatepass.service.UserService;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        User user = userService.getByEmail(request.email());
        String token = jwtService.generateToken(user.getEmail(),
                Map.of("role", user.getRole().name(), "userId", user.getId(), "name", user.getFullName()));
        return new AuthResponse(token, jwtService.getExpirationMs(), UserResponse.from(user));
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.from(userService.register(request)));
    }

    @GetMapping("/me")
    public UserResponse me(@AuthenticationPrincipal UserDetails principal) {
        return UserResponse.from(userService.getByEmail(principal.getUsername()));
    }
}
