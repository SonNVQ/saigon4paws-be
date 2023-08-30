package org.saigon4paws.Repositories;

import org.saigon4paws.Models.Adopter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdopterRepository extends JpaRepository<Adopter, Long> {
}
