package org.saigon4paws.Repositories;

import org.saigon4paws.Models.Enums.ERole;
import org.saigon4paws.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(ERole name);
}
