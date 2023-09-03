package org.saigon4paws.Services;

import org.saigon4paws.DTO.PetTypeDTO;
import org.saigon4paws.Models.PetType;

import java.util.List;

public interface PetTypeService {
    void createPetType(PetTypeDTO petTypeDTO) throws Exception;

    List<PetType> getAllPetTypes();

    PetTypeDTO getPetTypeById(Integer id);

    PetTypeDTO updatePetTypeById(Integer id, PetTypeDTO petTypeDTO) throws Exception;

    void deletePetTypeById(Integer id) throws Exception;
}
