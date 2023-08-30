package org.saigon4paws.Services;

import org.saigon4paws.DTO.UserDTO;
import org.saigon4paws.Models.User;

public interface UserService {
    void saveUser(UserDTO userDTO);

    User findUserByUsername(String username);

    User findUserByEmail(String email);
}
