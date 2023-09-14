package org.saigon4paws.Repositories;

import org.saigon4paws.Models.SponsorshipInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SponsorshipInformationRepository extends JpaRepository<SponsorshipInformation, Long> {
}
