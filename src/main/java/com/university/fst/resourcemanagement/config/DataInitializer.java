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
/*
        //  Chef Département
        if (!userRepository.existsByEmail("issamChefDepa@gmail.com")) {
            User chef = new User();
            chef.setNom("Chef");
            chef.setPrenom("Departement");
            chef.setEmail("issamChefDepa@gmail.com");
            chef.setPassword(passwordEncoder.encode("Chef@1234"));
            chef.setRole(Role.CHEF_DEPARTEMENT);
            chef.setStatus(Status.ACTIVE);
            userRepository.save(chef);
            System.out.println(" Chef département créé : issamChefDepa@gmail.com / Chef@1234");
        }

        //  Enseignant
        if (!userRepository.existsByEmail("issamProf@gmail.com")) {
            User enseignant = new User();
            enseignant.setNom("Issam");
            enseignant.setPrenom("Prof");
            enseignant.setEmail("issamProf@gmail.com");
            enseignant.setPassword(passwordEncoder.encode("Prof@1234"));
            enseignant.setRole(Role.ENSEIGNANT);
            enseignant.setStatus(Status.ACTIVE);
            userRepository.save(enseignant);
            System.out.println(" Enseignant créé : issamProf@gmail.com / Prof@1234");
        }
        //  Responsable Ressource
        if (!userRepository.existsByEmail("issamRRessource@gmail.com")) {
            User responsable = new User();
            responsable.setNom("Responsable");
            responsable.setPrenom("Ressource");
            responsable.setEmail("issamRRessource@gmail.com");
            responsable.setPassword(passwordEncoder.encode("Resp@1234"));
            responsable.setRole(Role.RESPONSABLE_RESOURCE);
            responsable.setStatus(Status.ACTIVE);
            userRepository.save(responsable);
            System.out.println(" Responsable créé : issamRRessource@gmail.com / Resp@1234");
        }
        //  Technicien
        if (!userRepository.existsByEmail("issamTechnicien@gmail.com")) {
            User responsable = new User();
            responsable.setNom("Issam");
            responsable.setPrenom("Tech");
            responsable.setEmail("issamTechnicien@gmail.com");
            responsable.setPassword(passwordEncoder.encode("Tech@1234"));
            responsable.setRole(Role.TECHNICIEN);
            responsable.setStatus(Status.ACTIVE);
            userRepository.save(responsable);
            System.out.println(" Responsable créé : issamTechnicien@gmail.com / Tech@1234");
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

 */
    }


}