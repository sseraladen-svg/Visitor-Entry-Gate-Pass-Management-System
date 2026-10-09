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

    @PostMapping("/visitor-login")
    public String visitorLogin(@RequestParam String email,
                               @RequestParam String password) {

        System.out.println("Visitor login attempt - Email: " + email);

        // TODO: Implement visitor authentication logic
        // For now, redirect to visitor dashboard
        return "redirect:/visitor-dashboard.html";
    }

    @PostMapping("/security-login")
    public String securityLogin(@RequestParam String securityId,
                                @RequestParam String password) {

        System.out.println("Security login attempt - Security ID: " + securityId);

        // TODO: Implement security authentication logic
        // For now, redirect to security dashboard
        return "redirect:/security-dashboard.html";
    }

    @PostMapping("/admin-login")
    public String adminLogin(@RequestParam String email,
                             @RequestParam String password) {

        System.out.println("Admin login attempt - Email: " + email);

        // TODO: Implement admin authentication logic
        // For now, redirect to admin dashboard
        return "redirect:/admin-dashboard.html";
    }

    @PostMapping("/register")
    public String register(@RequestParam String fullName,
                          @RequestParam String email,
                          @RequestParam String phone,
                          @RequestParam String company,
                          @RequestParam(required = false) String department,
                          @RequestParam(required = false) String idProofType,
                          @RequestParam(required = false) String idProofNumber,
                          @RequestParam String hostName,
                          @RequestParam String hostEmail,
                          @RequestParam String purpose,
                          @RequestParam String visitDate,
                          @RequestParam String visitTime,
                          @RequestParam(required = false) String vehicleNumber,
                          @RequestParam(required = false) String vehicleType,
                          @RequestParam(required = false) String vehicleModel,
                          @RequestParam(required = false) String vehicleColor) {

        System.out.println("Visitor registration - Email: " + email);

        // TODO: Implement visitor registration logic
        // Create visitor account and send password reset email
        return "redirect:/register.html?success=true";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email) {

        System.out.println("Password reset request - Email: " + email);

        // TODO: Implement password reset logic
        // Send password reset email
        return "redirect:/forgot-password.html?success=true";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword) {

        System.out.println("Password change request");

        // TODO: Implement password change logic
        if (newPassword.equals(confirmPassword)) {
            return "redirect:/change-password.html?success=true";
        }

        return "redirect:/change-password.html?error=true";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String newPassword,
                                @RequestParam String confirmPassword,
                                @RequestParam(required = false) String token) {

        System.out.println("Password reset with token");

        // TODO: Implement password reset with token logic
        if (newPassword.equals(confirmPassword)) {
            return "redirect:/reset-password.html?success=true";
        }

        return "redirect:/reset-password.html?error=true";
    }
}