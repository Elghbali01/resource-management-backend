package com.university.fst.resourcemanagement.config;

import com.university.fst.resourcemanagement.entity.User;
import com.university.fst.resourcemanagement.enums.Role;
import com.university.fst.resourcemanagement.enums.Status;
import com.university.fst.resourcemanagement.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository  userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository  = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        // Admin 1
        if (!userRepository.existsByEmail("issamelghbali@gmail.com")) {
            User admin = new User();
            admin.setNom("Elghbali");
            admin.setPrenom("Issam");
            admin.setEmail("issamelghbali@gmail.com");
            admin.setPassword(passwordEncoder.encode("Admin1234"));
            admin.setRole(Role.ADMIN);
            admin.setStatus(Status.ACTIVE);
            admin.setMustChangePassword(false); // ← admin jamais forcé
            userRepository.save(admin);
            System.out.println("✅ Admin créé : issamelghbali@gmail.com / Admin1234");
        }
    }
}