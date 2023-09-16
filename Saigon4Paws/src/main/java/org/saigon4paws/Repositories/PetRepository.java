package org.saigon4paws.Repositories;

import org.saigon4paws.Models.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    @Query("SELECT p.id FROM Pet p WHERE p.adopter IS NULL")
    List<Long> getPetIdsByAdopterIsNull();
}
