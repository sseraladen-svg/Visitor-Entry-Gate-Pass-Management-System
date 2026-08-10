package com.college.visitorgatepass.config;

import com.college.visitorgatepass.dto.AuthDtos.RegisterRequest;
import com.college.visitorgatepass.model.Role;
import com.college.visitorgatepass.repository.UserRepository;
import com.college.visitorgatepass.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    public CommandLineRunner seedUsers(UserRepository userRepository,
                                       UserService userService,
                                       @Value("${app.seed.enabled:true}") boolean seedEnabled,
                                       @Value("${app.seed.default-password:admin123}") String defaultPassword) {
        return args -> {
            if (!seedEnabled || userRepository.count() > 0) {
                return;
            }
            userService.register(new RegisterRequest("System Administrator", "admin@college.edu", defaultPassword,
                    Role.ADMIN, "Administration", "9000000001"));
            userService.register(new RegisterRequest("Gate Security", "security@college.edu", defaultPassword,
                    Role.SECURITY, "Security", "9000000002"));
            userService.register(new RegisterRequest("Dr. Anita Rao", "anita.rao@college.edu", defaultPassword,
                    Role.HOST, "Computer Science", "9000000003"));
            log.info("Seeded default users (admin@college.edu / security@college.edu / anita.rao@college.edu)");
        };
    }
}
