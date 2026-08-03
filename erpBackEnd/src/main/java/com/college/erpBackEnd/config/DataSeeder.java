package com.college.erpBackEnd.config;

import com.college.erpBackEnd.entity.Role;
import com.college.erpBackEnd.entity.RoleName;
import com.college.erpBackEnd.entity.User;
import com.college.erpBackEnd.repository.RoleRepository;
import com.college.erpBackEnd.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        createRoleIfAbsent(RoleName.ADMIN.name());
        createRoleIfAbsent(RoleName.STUDENT.name());
        createRoleIfAbsent(RoleName.FUCULTY.name());

        if (!userRepository.existsByUsername("admin")) {
            Role adminRole = roleRepository.findByName(RoleName.ADMIN.name())
                    .orElseThrow(() -> new IllegalStateException("ADMIN role not found"));

            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEnabled(true);
            admin.setRoles(Set.of(adminRole));
            userRepository.save(admin);
        }
    }

    private void createRoleIfAbsent(String roleName) {
        if (roleRepository.findByName(roleName).isEmpty()) {
            roleRepository.save(new Role(roleName));
        }
    }
}
