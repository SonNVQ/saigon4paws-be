package org.saigon4paws.Services;

import org.saigon4paws.DTO.SponsorshipInformationDTO;
import org.saigon4paws.Models.SponsorshipInformation;
import org.springframework.data.domain.Page;

public interface SponsorshipInformationService {
    Page<SponsorshipInformation> findAll(int page, int size);

    SponsorshipInformationDTO getSponsorshipInformationDTOById(Long id);

    SponsorshipInformationDTO createSponsorshipInformation(SponsorshipInformationDTO sponsorshipInformationDTO) throws Exception;

    SponsorshipInformationDTO updateSponsorshipInformation(SponsorshipInformationDTO sponsorshipInformationDTO) throws Exception;

    void deleteSponsorshipInformation(Long id) throws Exception;
}
