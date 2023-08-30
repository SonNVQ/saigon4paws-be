package org.saigon4paws.Repositories;

import org.saigon4paws.Models.PetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetTypeRepository extends JpaRepository<PetType, Integer> {
}
