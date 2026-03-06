package com.example.login.config;

import com.example.login.entity.Permission;
import com.example.login.entity.Role;
import com.example.login.entity.User;
import com.example.login.repository.PermissionRepository;
import com.example.login.repository.RoleRepository;
import com.example.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // 1. Create Permissions
        Permission read = createPermissionIfNotFound("UNIVERSITY_READ");
        Permission create = createPermissionIfNotFound("UNIVERSITY_CREATE");
        Permission update = createPermissionIfNotFound("UNIVERSITY_UPDATE");
        Permission delete = createPermissionIfNotFound("UNIVERSITY_DELETE");

        // 2. Create ADMIN Role and assign permissions
        Role adminRole = roleRepository.findByName("ADMIN").orElse(null);
        if (adminRole == null) {
            Set<Permission> adminPermissions = new HashSet<>();
            adminPermissions.add(read);
            adminPermissions.add(create);
            adminPermissions.add(update);
            adminPermissions.add(delete);

            adminRole = Role.builder()
                    .name("ADMIN")
                    .permissions(adminPermissions)
                    .build();
            roleRepository.save(adminRole);
        }

        // 3. Create USER Role and assign basic permissions
        Role userRole = roleRepository.findByName("USER").orElse(null);
        if (userRole == null) {
            Set<Permission> userPermissions = new HashSet<>();
            userPermissions.add(read);

            userRole = Role.builder()
                    .name("USER")
                    .permissions(userPermissions)
                    .build();
            roleRepository.save(userRole);
        }

        // 4. Create ADMIN User
        if (userRepository.findByUsername("admin").isEmpty()) {
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);

            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .enabled(true)
                    .roles(adminRoles)
                    .build();
            userRepository.save(admin);
        }
    }

    private Permission createPermissionIfNotFound(String name) {
        return permissionRepository.findByName(name)
                .orElseGet(() -> permissionRepository.save(Permission.builder().name(name).build()));
    }
}
