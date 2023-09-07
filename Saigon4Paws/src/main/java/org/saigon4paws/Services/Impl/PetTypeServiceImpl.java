package org.saigon4paws.Services.Impl;

import org.saigon4paws.DTO.PetTypeDTO;
import org.saigon4paws.Models.PetType;
import org.saigon4paws.Repositories.PetTypeRepository;
import org.saigon4paws.Services.PetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetTypeServiceImpl implements PetTypeService {
    @Autowired
    private PetTypeRepository petTypeRepository;

    @Override
    public void createPetType(PetTypeDTO petTypeDTO) throws Exception {
        PetType existingPetType = petTypeRepository.findByNameAndSpecies(petTypeDTO.getName(), petTypeDTO.getSpecies());
        if (existingPetType != null) {
            throw new Exception("Pet type already exists!");
        }
        PetType petType = PetType.builder()
                .name(petTypeDTO.getName())
                .species(petTypeDTO.getSpecies())
                .build();
        petTypeRepository.save(petType);
    }

    @Override
    public List<PetType> getAllPetTypes() {
        return petTypeRepository.findAll();
    }

    @Override
    public PetType getPetTypeById(Integer id) {
        if (id == null)
            return null;
        return petTypeRepository.findById(id).orElse(null);
    }

    @Override
    public PetTypeDTO getPetTypeDTOById(Integer id) {
        PetType petType = getPetTypeById(id);
        if (petType == null)
            return null;
        return PetTypeDTO.builder()
                .id(petType.getId())
                .name(petType.getName())
                .species(petType.getSpecies())
                .build();
    }

    @Override
    public PetTypeDTO updatePetTypeDTOById(Integer id, PetTypeDTO petTypeDTO) throws Exception {
        PetType petType = getPetTypeById(id);
        if (petType == null)
            throw new Exception("Pet type not found!");
        petType.setName(petTypeDTO.getName());
        petType.setSpecies(petTypeDTO.getSpecies());
        petTypeRepository.save(petType);
        return PetTypeDTO.builder()
                .id(petType.getId())
                .name(petType.getName())
                .species(petType.getSpecies())
                .build();
    }

    @Override
    public void deletePetTypeById(Integer id) throws Exception {
        PetType petType = getPetTypeById(id);
        if (petType == null)
            throw new Exception("Pet type not found!");
        petTypeRepository.delete(petType);
    }
}
