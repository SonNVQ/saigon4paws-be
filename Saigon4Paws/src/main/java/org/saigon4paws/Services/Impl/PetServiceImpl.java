package org.saigon4paws.Services.Impl;

import org.saigon4paws.DTO.PetDTO;
import org.saigon4paws.Models.Pet;
import org.saigon4paws.Models.PetType;
import org.saigon4paws.Models.ReliefGroup;
import org.saigon4paws.Repositories.PetRepository;
import org.saigon4paws.Services.FileService;
import org.saigon4paws.Services.PetService;
import org.saigon4paws.Services.PetTypeService;
import org.saigon4paws.Services.ReliefGroupService;
import org.saigon4paws.Utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PetServiceImpl implements PetService {
    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetTypeService petTypeService;

    @Autowired
    private ReliefGroupService reliefGroupService;

    @Autowired
    private FileService fileService;

    @Value("${upload.pet-photo.dir}")
    private String petPhotoDir;

    @Override
    public Page<Pet> findAll(int page, int size) {
        if (page < 0)
            page = Constants.DEFAULT_PAGE_NUMBER;
        if (size < 0 || size > Constants.MAX_PAGE_SIZE)
            size = Constants.DEFAULT_PAGE_SIZE;
        Pageable pageable = PageRequest.of(page, size);
        return petRepository.findAll(pageable);
    }

    @Override
    public Pet getPetById(Long id) {
        if (id == null)
            return null;
        return petRepository.findById(id).orElse(null);
    }

    @Override
    public PetDTO getPetDTOById(Long id) {
        Pet pet = getPetById(id);
        if (pet == null)
            return null;
        PetDTO petDTO = PetDTO.builder()
                .name(pet.getName())
                .gender(pet.getGender())
                .age(pet.getAge())
                .photoUrl(pet.getPhotoUrl())
                .adoptionStatus(pet.getAdoptionStatus())
                .healthStatus(pet.getHealthStatus())
                .vaccinationStatus(pet.getVaccinationStatus())
                .adoptedDate(pet.getAdoptedDate())
                .petTypeId(pet.getType().getId())
                .reliefGroupId(pet.getReliefGroup().getId())
                .build();
        if (pet.getAdopter() != null)
            petDTO.setAdopterId(pet.getAdopter().getId());
        return petDTO;
    }

    @Override
    public PetDTO createPet(PetDTO petDTO) {
        PetType petType = petTypeService.getPetTypeById(petDTO.getPetTypeId());
        ReliefGroup reliefGroup = reliefGroupService.getReliefGroupById(petDTO.getReliefGroupId());
        Pet pet = Pet.builder()
                .name(petDTO.getName())
                .gender(petDTO.getGender())
                .age(petDTO.getAge())
                .photoUrl(petDTO.getPhotoUrl())
                .adoptionStatus(petDTO.getAdoptionStatus())
                .healthStatus(petDTO.getHealthStatus())
                .vaccinationStatus(petDTO.getVaccinationStatus())
                .adoptedDate(null)
                .type(petType)
                .adopter(null)
                .reliefGroup(reliefGroup)
                .build();
        petRepository.save(pet);
        return petDTO;
    }

    @Override
    public PetDTO updatePet(Long id, PetDTO petDTO) throws Exception {
        Pet pet = getPetById(id);
        PetType petType = petTypeService.getPetTypeById(petDTO.getPetTypeId());
        ReliefGroup reliefGroup = reliefGroupService.getReliefGroupById(petDTO.getReliefGroupId());
        pet.setName(petDTO.getName());
        pet.setGender(petDTO.getGender());
        pet.setAge(petDTO.getAge());
        if (petDTO.getPhotoUrl() != null)
            pet.setPhotoUrl(petDTO.getPhotoUrl());
        pet.setAdoptionStatus(petDTO.getAdoptionStatus());
        pet.setHealthStatus(petDTO.getHealthStatus());
        pet.setVaccinationStatus(petDTO.getVaccinationStatus());
        pet.setAdoptedDate(petDTO.getAdoptedDate());
        pet.setType(petType);
        pet.setReliefGroup(reliefGroup);
        petRepository.save(pet);
        return getPetDTOById(id);
    }

    @Override
    public void deletePet(Long id) throws Exception {
        Pet pet = getPetById(id);
        if (pet == null)
            throw new Exception("Pet not found!");
        petRepository.delete(pet);
    }

    @Override
    public String uploadPetPhoto(MultipartFile avatar) throws Exception {
        return fileService.saveImageFromMultipartFile(petPhotoDir, avatar);
    }
}
