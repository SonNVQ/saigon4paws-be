package org.saigon4paws.Services.Impl;

import org.saigon4paws.DTO.UserDTO;
import org.saigon4paws.Models.Enums.ERole;
import org.saigon4paws.Models.Role;
import org.saigon4paws.Models.User;
import org.saigon4paws.Repositories.RoleRepository;
import org.saigon4paws.Repositories.UserRepository;
import org.saigon4paws.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserDTO userDTO) {
        Role userRole = roleRepository.findByName(ERole.ROLE_USER);
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        User user = User.builder()
                .username(userDTO.getUsername())
                .password(encodedPassword)
                .fullName(userDTO.getFullName())
                .email(userDTO.getEmail())
                .roles(roles)
                .build();
        userRepository.save(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

}
