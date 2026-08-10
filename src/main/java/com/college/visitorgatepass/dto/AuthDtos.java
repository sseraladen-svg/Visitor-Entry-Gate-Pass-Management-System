package com.college.visitorgatepass.dto;

import com.college.visitorgatepass.model.Role;
import com.college.visitorgatepass.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public final class AuthDtos {

    private AuthDtos() {
    }

    public record LoginRequest(
            @NotBlank @Email String email,
            @NotBlank String password) {
    }

    public record RegisterRequest(
            @NotBlank String fullName,
            @NotBlank @Email String email,
            @NotBlank @Size(min = 6, message = "password must be at least 6 characters") String password,
            @NotNull Role role,
            String department,
            String phone) {
    }

    public record UserResponse(
            Long id,
            String fullName,
            String email,
            Role role,
            String department,
            String phone,
            boolean active) {

        public static UserResponse from(User user) {
            return new UserResponse(user.getId(), user.getFullName(), user.getEmail(), user.getRole(),
                    user.getDepartment(), user.getPhone(), user.isActive());
        }
    }

    public record AuthResponse(String token, long expiresInMs, UserResponse user) {
    }
}
