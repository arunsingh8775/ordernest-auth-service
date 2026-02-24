package com.example.authservice.config;

import com.example.authservice.entity.User;
import com.example.authservice.repository.UserRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@ConditionalOnProperty(value = "app.seed.users-enabled", havingValue = "true", matchIfMissing = true)
public class UserDataInitializer implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(UserDataInitializer.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        List<SeedUser> seedUsers = List.of(
                new SeedUser("admin@ordernest.com", "Admin@1234", "Order", "Admin", "ADMIN"),
                new SeedUser("arun@ordernest.com", "User@1234", "Arun", "Singh", "USER"),
                new SeedUser("priya@ordernest.com", "User@1234", "Priya", "Sharma", "USER"),
                new SeedUser("rahul@ordernest.com", "User@1234", "Rahul", "Verma", "USER"),
                new SeedUser("neha@ordernest.com", "User@1234", "Neha", "Gupta", "USER")
        );

        int inserted = 0;
        for (SeedUser seedUser : seedUsers) {
            String normalizedEmail = seedUser.email().trim().toLowerCase();
            if (userRepository.existsByEmail(normalizedEmail)) {
                continue;
            }

            User user = new User();
            user.setEmail(normalizedEmail);
            user.setPasswordHash(passwordEncoder.encode(seedUser.rawPassword()));
            user.setFirstName(seedUser.firstName());
            user.setLastName(seedUser.lastName());
            user.setRole(seedUser.role());
            user.setActive(true);
            userRepository.save(user);
            inserted++;
        }

        logger.info("User seed completed. inserted={}, skipped={}", inserted, seedUsers.size() - inserted);
    }

    private record SeedUser(
            String email,
            String rawPassword,
            String firstName,
            String lastName,
            String role
    ) {
    }
}
