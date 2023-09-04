package org.saigon4paws.Repositories;

import org.saigon4paws.Models.PetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetTypeRepository extends JpaRepository<PetType, Integer> {
    PetType findByNameAndSpecies(String name, String species);
}
