package org.saigon4paws.Services.Impl;

import org.saigon4paws.DTO.AdopterDTO;
import org.saigon4paws.Models.Adopter;
import org.saigon4paws.Models.Pet;
import org.saigon4paws.Repositories.AdopterRepository;
import org.saigon4paws.Repositories.PetRepository;
import org.saigon4paws.Services.AdopterService;
import org.saigon4paws.Services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdopterServiceImpl implements AdopterService {
    @Autowired
    private AdopterRepository adopterRepository;

    @Autowired
    private PetRepository petRepository;

    @Override
    public AdopterDTO saveAdopter(AdopterDTO adopterDTO) {
        Pet pet = petRepository.findById(adopterDTO.getPetId()).orElse(null);
        List<Pet> pets = new ArrayList<>();
        if (pet != null) {
            adopterDTO.setPetId(pet.getId());
            pets.add(pet);
        }
        Adopter adopter = Adopter.builder()
                .fullName(adopterDTO.getFullName())
                .dob(adopterDTO.getDob())
                .address(adopterDTO.getAddress())
                .formOfStay(adopterDTO.getFormOfStay())
                .job(adopterDTO.getJob())
                .monthlyIncome(adopterDTO.getMonthlyIncome())
                .numberOfPeopleLivingTogether(adopterDTO.getNumberOfPeopleLivingTogether())
                .phoneNumber(adopterDTO.getPhoneNumber())
                .pets(pets)
                .build();
        adopterRepository.save(adopter);
        return adopterDTO;
    }

    @Override
    public AdopterDTO getAdopterById(Long id) {
        return null;
    }
}
