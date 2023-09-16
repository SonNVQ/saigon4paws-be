package org.saigon4paws.Services.Impl;

import org.saigon4paws.DTO.SponsorshipInformationDTO;
import org.saigon4paws.Models.ReliefGroup;
import org.saigon4paws.Models.SponsorshipInformation;
import org.saigon4paws.Repositories.SponsorshipInformationRepository;
import org.saigon4paws.Services.ReliefGroupService;
import org.saigon4paws.Services.SponsorshipInformationService;
import org.saigon4paws.Utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SponsorshipInformationServiceImpl implements SponsorshipInformationService {
    @Autowired
    private SponsorshipInformationRepository sponsorshipInformationRepository;

    @Autowired
    private ReliefGroupService reliefGroupService;

    @Override
    public Page<SponsorshipInformation> findAll(int page, int size) {
        if (page < 0)
            page = Constants.DEFAULT_PAGE_NUMBER;
        if (size < 0 || size > Constants.MAX_PAGE_SIZE)
            size = Constants.DEFAULT_PAGE_SIZE;
        Pageable pageable = PageRequest.of(page, size);
        return sponsorshipInformationRepository.findAll(pageable);
    }

    @Override
    public SponsorshipInformationDTO getSponsorshipInformationDTOById(Long id) {
        SponsorshipInformation sponsorshipInformation = sponsorshipInformationRepository.findById(id).orElse(null);
        if (sponsorshipInformation == null) {
            return null;
        }
        SponsorshipInformationDTO savedSponsorshipInformationDTO = SponsorshipInformationDTO.builder()
                .id(sponsorshipInformation.getId())
                .sponsorName(sponsorshipInformation.getSponsorName())
                .amount(sponsorshipInformation.getAmount())
                .message(sponsorshipInformation.getMessage())
                .status(sponsorshipInformation.getStatus())
                .reliefGroupId(sponsorshipInformation.getReliefGroup().getId())
                .build();
        return savedSponsorshipInformationDTO;
    }

    @Override
    public SponsorshipInformationDTO createSponsorshipInformation(SponsorshipInformationDTO sponsorshipInformationDTO) {
        ReliefGroup reliefGroup = reliefGroupService.getReliefGroupById(sponsorshipInformationDTO.getReliefGroupId());
        SponsorshipInformation sponsorshipInformation = SponsorshipInformation.builder()
                .sponsorName(sponsorshipInformationDTO.getSponsorName())
                .amount(sponsorshipInformationDTO.getAmount())
                .message(sponsorshipInformationDTO.getMessage())
                .status(sponsorshipInformationDTO.getStatus())
                .reliefGroup(reliefGroup)
                .build();
        SponsorshipInformation savedSponsorshipInformation = sponsorshipInformationRepository.save(sponsorshipInformation);
        SponsorshipInformationDTO savedSponsorshipInformationDTO = SponsorshipInformationDTO.builder()
                .id(savedSponsorshipInformation.getId())
                .sponsorName(savedSponsorshipInformation.getSponsorName())
                .amount(savedSponsorshipInformation.getAmount())
                .message(savedSponsorshipInformation.getMessage())
                .status(savedSponsorshipInformation.getStatus())
                .reliefGroupId(savedSponsorshipInformation.getReliefGroup().getId())
                .build();
        return savedSponsorshipInformationDTO;
    }

    @Override
    public SponsorshipInformationDTO updateSponsorshipInformation(SponsorshipInformationDTO sponsorshipInformationDTO) {
        SponsorshipInformation sponsorshipInformation = sponsorshipInformationRepository.findById(sponsorshipInformationDTO.getId()).orElse(null);
        if (sponsorshipInformation == null) {
            throw new RuntimeException("Sponsorship information not found!");
        }
        sponsorshipInformation.setSponsorName(sponsorshipInformationDTO.getSponsorName());
        sponsorshipInformation.setAmount(sponsorshipInformationDTO.getAmount());
        sponsorshipInformation.setMessage(sponsorshipInformationDTO.getMessage());
        sponsorshipInformation.setStatus(sponsorshipInformationDTO.getStatus());
        sponsorshipInformationRepository.save(sponsorshipInformation);
        return sponsorshipInformationDTO;
    }

    @Override
    public void deleteSponsorshipInformation(Long id) throws Exception {
        SponsorshipInformation sponsorshipInformation = sponsorshipInformationRepository.findById(id).orElse(null);
        if (sponsorshipInformation == null) {
            throw new Exception("Sponsorship information not found!");
        }
        sponsorshipInformationRepository.delete(sponsorshipInformation);
    }
}
