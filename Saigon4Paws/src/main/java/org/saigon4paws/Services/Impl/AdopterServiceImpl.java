package org.saigon4paws.Services.Impl;

import org.saigon4paws.DTO.AdopterDTO;
import org.saigon4paws.Models.Adopter;
import org.saigon4paws.Models.Pet;
import org.saigon4paws.Repositories.AdopterRepository;
import org.saigon4paws.Repositories.PetRepository;
import org.saigon4paws.Services.AdopterService;
import org.saigon4paws.Services.PetService;
import org.saigon4paws.Utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
//        List<Pet> pets = new ArrayList<>();
//        if (pet != null) {
//            adopterDTO.setPetId(pet.getId());
//            pets.add(pet);
//        }
        Adopter adopter = Adopter.builder()
                .fullName(adopterDTO.getFullName())
                .dob(adopterDTO.getDob())
                .address(adopterDTO.getAddress())
                .formOfStay(adopterDTO.getFormOfStay())
                .job(adopterDTO.getJob())
                .monthlyIncome(adopterDTO.getMonthlyIncome())
                .numberOfPeopleLivingTogether(adopterDTO.getNumberOfPeopleLivingTogether())
                .phoneNumber(adopterDTO.getPhoneNumber())
                .status(adopterDTO.getStatus())
                .pet(pet)
                .build();
        adopterRepository.save(adopter);
        return adopterDTO;
    }

    @Override
    public Page<Adopter> findAll(int page, int size) {
        if (page < 0)
            page = Constants.DEFAULT_PAGE_NUMBER;
        if (size < 0 || size > Constants.MAX_PAGE_SIZE)
            size = Constants.DEFAULT_PAGE_SIZE;
        Pageable pageable = PageRequest.of(page, size);
        return adopterRepository.findAll(pageable);
    }

    @Override
    public Adopter getAdopterById(Long id) {
        if (id == null)
            return null;
        return adopterRepository.findById(id).orElse(null);
    }

    private AdopterDTO getAdopterDTO(Adopter adopter) {
        return AdopterDTO.builder()
                .id(adopter.getId())
                .fullName(adopter.getFullName())
                .dob(adopter.getDob())
                .address(adopter.getAddress())
                .formOfStay(adopter.getFormOfStay())
                .job(adopter.getJob())
                .monthlyIncome(adopter.getMonthlyIncome())
                .numberOfPeopleLivingTogether(adopter.getNumberOfPeopleLivingTogether())
                .phoneNumber(adopter.getPhoneNumber())
                .petId(adopter.getPet().getId())
                .createdAt(adopter.getCreatedAt())
                .build();
    }

    @Override
    public AdopterDTO getAdopterDTOById(Long id) {
        Adopter adopter = getAdopterById(id);
        if (adopter == null)
            return null;
        return getAdopterDTO(adopter);
    }
    
    @Override
    public AdopterDTO updateAdopter(Long id, AdopterDTO petDTO) throws Exception {
        Adopter adopter = adopterRepository.findById(id).orElse(null);
        if (adopter == null)
            throw new Exception("Adopter not found!");
        Pet pet = petRepository.findById(petDTO.getPetId()).orElse(null);
//        if (pet == null)
//            throw new Exception("Pet not found!");
//        List<Pet> pets = new ArrayList<>();
//        pets.add(pet);
        adopter.setFullName(petDTO.getFullName());
        adopter.setDob(petDTO.getDob());
        adopter.setAddress(petDTO.getAddress());
        adopter.setFormOfStay(petDTO.getFormOfStay());
        adopter.setJob(petDTO.getJob());
        adopter.setMonthlyIncome(petDTO.getMonthlyIncome());
        adopter.setNumberOfPeopleLivingTogether(petDTO.getNumberOfPeopleLivingTogether());
        adopter.setPhoneNumber(petDTO.getPhoneNumber());
        adopter.setPet(pet);
        Adopter savedAdopter = adopterRepository.save(adopter);
        return getAdopterDTO(savedAdopter);
    }

    @Override
    public void deleteAdopter(Long id) throws Exception {
        Adopter adopter = adopterRepository.findById(id).orElse(null);
        if (adopter == null)
            throw new Exception("Adopter not found!");
        adopterRepository.delete(adopter);
    }

    @Override
    public AdopterDTO changeAdoptStatus(Long id, String status) throws Exception {
        Adopter adopter = getAdopterById(id);
        if (adopter == null)
            throw new Exception("Adopter not found!");
        adopter.setStatus(status);
        Adopter savedAdopter = adopterRepository.save(adopter);
        return getAdopterDTO(savedAdopter);
    }
}
