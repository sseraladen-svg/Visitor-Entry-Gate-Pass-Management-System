package com.visiongate;

import com.visiongate.entity.VisitorEntity;
import com.visiongate.entity.UserEntity;
import com.visiongate.repository.VisitorRepository;
import com.visiongate.repository.UserRepository;
import com.visiongate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private UserRepository userRepository;

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

        var visitorOpt = visitorRepository.findByEmail(email);
        if (visitorOpt.isPresent()) {
            VisitorEntity visitor = visitorOpt.get();
            if (visitor.getPassword() != null && visitor.getPassword().equals(password)) {
                System.out.println("Visitor login successful: " + email);
                return "redirect:/visitor-dashboard.html?email=" + email;
            }
        }

        System.out.println("Visitor login failed: " + email);
        return "redirect:/login.html?error=Invalid credentials";
    }

    @PostMapping("/security-login")
    public String securityLogin(@RequestParam String securityId,
                                @RequestParam String password) {

        System.out.println("Security login attempt - Security ID: " + securityId);

        // Check if security user exists in users table
        UserEntity user = userRepository.findByUsername(securityId);
        if (user != null && "SECURITY".equals(user.getRole()) && user.getPassword().equals(password)) {
            System.out.println("Security login successful: " + securityId);
            return "redirect:/security-dashboard.html";
        }

        // Check visitor table for security role
        var visitorOpt = visitorRepository.findByEmail(securityId);
        if (visitorOpt.isPresent()) {
            VisitorEntity visitor = visitorOpt.get();
            if ("SECURITY".equals(visitor.getRole()) && visitor.getPassword() != null && visitor.getPassword().equals(password)) {
                System.out.println("Security login successful: " + securityId);
                return "redirect:/security-dashboard.html";
            }
        }

        System.out.println("Security login failed: " + securityId);
        return "redirect:/login.html?error=Invalid credentials";
    }

    @PostMapping("/admin-login")
    public String adminLogin(@RequestParam String email,
                             @RequestParam String password) {

        System.out.println("Admin login attempt - Email: " + email);

        // Check if admin user exists in users table
        UserEntity user = userRepository.findByUsername(email);
        if (user != null && "ADMIN".equals(user.getRole()) && user.getPassword().equals(password)) {
            System.out.println("Admin login successful: " + email);
            return "redirect:/admin-dashboard.html";
        }

        // Check visitor table for admin role
        var visitorOpt = visitorRepository.findByEmail(email);
        if (visitorOpt.isPresent()) {
            VisitorEntity visitor = visitorOpt.get();
            if ("ADMIN".equals(visitor.getRole()) && visitor.getPassword() != null && visitor.getPassword().equals(password)) {
                System.out.println("Admin login successful: " + email);
                return "redirect:/admin-dashboard.html";
            }
        }

        System.out.println("Admin login failed: " + email);
        return "redirect:/login.html?error=Invalid credentials";
    }

    @PostMapping("/register")
    public String register(@RequestParam String fullName,
                          @RequestParam String email,
                          @RequestParam String phone,
                          @RequestParam String password,
                          @RequestParam String company,
                          @RequestParam(required = false) String department,
                          @RequestParam(required = false) String idProofType,
                          @RequestParam(required = false) String idProofNumber) {

        System.out.println("Visitor registration - Email: " + email);

        try {
            // Check if email already exists
            if (visitorRepository.findByEmail(email).isPresent()) {
                System.out.println("Email already exists: " + email);
                return "redirect:/register.html?error=Email already registered";
            }

            // Create visitor entity
            VisitorEntity visitor = new VisitorEntity();
            visitor.setName(fullName);
            visitor.setEmail(email);
            visitor.setPhone(phone);
            visitor.setPassword(password);
            visitor.setCompany(company);
            visitor.setDepartment(department);
            visitor.setIdProofType(idProofType);
            visitor.setIdProofNumber(idProofNumber);
            visitor.setRole("VISITOR");
            visitor.setStatus("ACTIVE");

            visitorRepository.save(visitor);
            System.out.println("Visitor registered successfully: " + email);

            return "redirect:/register.html?success=true";
        } catch (Exception e) {
            System.out.println("Registration failed: " + e.getMessage());
            e.printStackTrace();
            return "redirect:/register.html?error=Registration failed";
        }
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email) {

        System.out.println("Password reset request - Email: " + email);

        var visitorOpt = visitorRepository.findByEmail(email);
        if (visitorOpt.isPresent()) {
            System.out.println("Password reset email sent to: " + email);
            return "redirect:/forgot-password.html?success=true";
        }

        System.out.println("Email not found: " + email);
        return "redirect:/forgot-password.html?error=Email not found";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword) {

        System.out.println("Password change request");

        if (!newPassword.equals(confirmPassword)) {
            return "redirect:/change-password.html?error=Passwords do not match";
        }

        // TODO: Implement actual password change logic with session
        return "redirect:/change-password.html?success=true";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String newPassword,
                                @RequestParam String confirmPassword,
                                @RequestParam(required = false) String token) {

        System.out.println("Password reset with token");

        if (!newPassword.equals(confirmPassword)) {
            return "redirect:/reset-password.html?error=Passwords do not match";
        }

        // TODO: Implement password reset with token validation
        return "redirect:/reset-password.html?success=true";
    }
}