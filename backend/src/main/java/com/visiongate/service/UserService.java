package com.visiongate.service;

import com.visiongate.entity.UserEntity;
import com.visiongate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }
    
    public boolean validateCredentials(String username, String password) {
        UserEntity user = userRepository.findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }
}