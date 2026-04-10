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

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        //  Admin 1
        if (!userRepository.existsByEmail("issamelghbali@gmail.com")) {
            User admin = new User();
            admin.setNom("Elghbali");
            admin.setPrenom("Issam");
            admin.setEmail("issamelghbali@gmail.com");
            admin.setPassword(passwordEncoder.encode("Admin1234"));
            admin.setRole(Role.ADMIN);
            admin.setStatus(Status.ACTIVE);
            userRepository.save(admin);
            System.out.println(" Admin créé : issamelghbali@gmail.com / Admin1234");
        }

        // Admin 2 (celui demandé en plus)
        if (!userRepository.existsByEmail("admin2@fst.ma")) {
            User admin2 = new User();
            admin2.setNom("Bennani");
            admin2.setPrenom("Fatima");
            admin2.setEmail("admin2@fst.ma");
            admin2.setPassword(passwordEncoder.encode("Admin@5678"));
            admin2.setRole(Role.ADMIN);
            admin2.setStatus(Status.ACTIVE);
            userRepository.save(admin2);
            System.out.println(" Admin 2 créé : admin2@fst.ma / Admin@5678");
        }

        //  Chef Département
        if (!userRepository.existsByEmail("chef@fst.ma")) {
            User chef = new User();
            chef.setNom("Idrissi");
            chef.setPrenom("Youssef");
            chef.setEmail("chef@fst.ma");
            chef.setPassword(passwordEncoder.encode("Chef@1234"));
            chef.setRole(Role.CHEF_DEPARTEMENT);
            chef.setStatus(Status.ACTIVE);
            userRepository.save(chef);
            System.out.println(" Chef département créé : chef@fst.ma / Chef@1234");
        }

        //  Enseignant
        if (!userRepository.existsByEmail("enseignant@fst.ma")) {
            User enseignant = new User();
            enseignant.setNom("Moussaoui");
            enseignant.setPrenom("Amine");
            enseignant.setEmail("enseignant@fst.ma");
            enseignant.setPassword(passwordEncoder.encode("Ens@1234"));
            enseignant.setRole(Role.ENSEIGNANT);
            enseignant.setStatus(Status.ACTIVE);
            userRepository.save(enseignant);
            System.out.println(" Enseignant créé : enseignant@fst.ma / Ens@1234");
        }

        //  Responsable Ressource
        if (!userRepository.existsByEmail("responsable@fst.ma")) {
            User responsable = new User();
            responsable.setNom("Chraibi");
            responsable.setPrenom("Sara");
            responsable.setEmail("responsable@fst.ma");
            responsable.setPassword(passwordEncoder.encode("Resp@1234"));
            responsable.setRole(Role.RESPONSABLE_RESOURCE);
            responsable.setStatus(Status.ACTIVE);
            userRepository.save(responsable);
            System.out.println(" Responsable créé : responsable@fst.ma / Resp@1234");
        }

        //  User INACTIVE pour tester le rejet
        if (!userRepository.existsByEmail("inactif@fst.ma")) {
            User inactif = new User();
            inactif.setNom("Test");
            inactif.setPrenom("Inactif");
            inactif.setEmail("inactif@fst.ma");
            inactif.setPassword(passwordEncoder.encode("Inactif@1234"));
            inactif.setRole(Role.ENSEIGNANT);
            inactif.setStatus(Status.INACTIVE);
            userRepository.save(inactif);
            System.out.println(" User inactif créé : inactif@fst.ma / Inactif@1234");
        }
    }
}