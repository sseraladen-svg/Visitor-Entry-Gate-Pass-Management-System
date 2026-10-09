package com.visiongate;

import com.visiongate.entity.UserEntity;
import com.visiongate.entity.VisitorEntity;
import com.visiongate.repository.UserRepository;
import com.visiongate.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Initializing default users...");

        // Create default admin user
        if (userRepository.findByUsername("admin") == null) {
            UserEntity admin = new UserEntity();
            admin.setUsername("admin");
            admin.setPassword("admin123");
            admin.setRole("ADMIN");
            userRepository.save(admin);
            System.out.println("Default admin user created: admin/admin123");
        }

        // Create default security user
        if (userRepository.findByUsername("security") == null) {
            UserEntity security = new UserEntity();
            security.setUsername("security");
            security.setPassword("security123");
            security.setRole("SECURITY");
            userRepository.save(security);
            System.out.println("Default security user created: security/security123");
        }

        // Create default visitor
        if (visitorRepository.findByEmail("visitor@test.com").isEmpty()) {
            VisitorEntity visitor = new VisitorEntity();
            visitor.setName("Test Visitor");
            visitor.setEmail("visitor@test.com");
            visitor.setPhone("1234567890");
            visitor.setPassword("visitor123");
            visitor.setRole("VISITOR");
            visitor.setStatus("ACTIVE");
            visitorRepository.save(visitor);
            System.out.println("Default visitor created: visitor@test.com/visitor123");
        }

        System.out.println("Data initialization complete.");
    }
}
