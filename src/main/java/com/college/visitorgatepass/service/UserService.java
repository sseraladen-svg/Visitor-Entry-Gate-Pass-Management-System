package com.college.visitorgatepass.service;

import com.college.visitorgatepass.dto.AuthDtos.RegisterRequest;
import com.college.visitorgatepass.exception.BadRequestException;
import com.college.visitorgatepass.exception.ResourceNotFoundException;
import com.college.visitorgatepass.model.Role;
import com.college.visitorgatepass.model.User;
import com.college.visitorgatepass.repository.UserRepository;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User register(RegisterRequest request) {
        if (userRepository.existsByEmailIgnoreCase(request.email())) {
            throw new BadRequestException("A user with email " + request.email() + " already exists");
        }
        User user = new User();
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(request.role());
        user.setDepartment(request.department());
        user.setPhone(request.phone());
        user.setActive(true);
        return userRepository.save(user);
    }

    public User getByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + email));
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findHosts() {
        return userRepository.findByRole(Role.HOST);
    }
}
