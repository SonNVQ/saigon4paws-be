package org.saigon4paws.Initializations;

import org.saigon4paws.Models.Enums.ERole;
import org.saigon4paws.Models.Role;
import org.saigon4paws.Models.User;
import org.saigon4paws.Repositories.RoleRepository;
import org.saigon4paws.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Profile("dev")
@Order(value = 2)
@Component
public class DevUserInitialization implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Role userRole = roleRepository.findByName(ERole.ROLE_USER);
        Role managerRole = roleRepository.findByName(ERole.ROLE_MANAGER);
        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);
        Set<Role> userRoles = Stream.of(userRole).collect(Collectors.toCollection(HashSet::new));
        Set<Role> managerRoles = Stream.of(userRole, managerRole).collect(Collectors.toCollection(HashSet::new));
        Set<Role> adminRoles = Stream.of(userRole, managerRole, adminRole).collect(Collectors.toCollection(HashSet::new));
        User normalUser = User.builder()
                .username("user")
                .password(passwordEncoder.encode("123456"))
                .fullName("Normal User")
                .email("user@s4p.io.vn")
                .roles(userRoles)
                .build();
        User managerUser = User.builder()
                .username("manager")
                .password(passwordEncoder.encode("123456"))
                .fullName("Manager")
                .email("manager@s4p.io.vn")
                .roles(managerRoles)
                .build();
        User adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("123456"))
                .fullName("Admin")
                .email("admin@s4p.io.vn")
                .roles(adminRoles)
                .build();
        List<User> users = Stream.of(normalUser, managerUser, adminUser).toList();
        users.forEach(user -> {
            if (userRepository.findByUsername(user.getUsername()).isEmpty())
                userRepository.save(user);
        });
    }
}
