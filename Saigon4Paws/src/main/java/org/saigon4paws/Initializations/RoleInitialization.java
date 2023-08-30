package org.saigon4paws.Initializations;

import org.saigon4paws.Models.Enums.ERole;
import org.saigon4paws.Models.Role;
import org.saigon4paws.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(value = 1)
@Component
public class RoleInitialization implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        ERole[] roles = ERole.values();
        for (ERole role : roles) {
            Role existingRole = roleRepository.findByName(role);
            if (existingRole == null)
                roleRepository.save(Role.builder().name(role).build());
        }
    }
}
